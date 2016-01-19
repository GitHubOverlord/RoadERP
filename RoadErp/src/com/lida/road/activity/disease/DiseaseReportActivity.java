package com.lida.road.activity.disease;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.base.BaseConnectTemplet;
import com.jun.android_frame.http.utils.AsyncUploadFiles;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.main.ChiocePeopleActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.entity.UserMessage;
import com.lida.road.utils.StringAdapter;
import com.loopj.android.http.RequestParams;

/**
 * 上报的页面
 * 
 * @author Administrator
 * 
 */
public class DiseaseReportActivity extends MainBaseActivity {
	private Button report, choicePeople;
	private EditText message;
	private TextView peopleNameTextView;
	public static final int REQUEST_CODE_NAME = 111;// 返回人名和ID用的startactivity的code
	public static final String BUNDLE_NAME = "bundle_name";
	public static final String BUNDLE_NAME_ID = "bundle_name_id";
	private String peopleName = "";
	private String peopleNameId = "";
	/**
	 * 根据传过来的值判断是从哪个功能来的
	 */
	public static final String BUNDLE_MARK = "bundle_mark";
	/**
	 * 从病害信详情息来的
	 */
	public static final int BUNDLE_FROM_DISEASE_DETAILS = 1;
	/**
	 * 从病害信息编辑页面来的
	 */
	public static final int BUNDLE_FROM_DISEASE_EDIT = 2;
	/**
	 * 病害信息的ID
	 */
	public static final String BUNDLE_VALUE_DISEASE_ID = "disease_id";
	/**
	 * 病害信息的一整个OBJECT
	 */
	public static final String BUNDLE_VALUE_DISEASE_MESSAGE = "disease_message";
	int mark = 0;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_disease);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "上报");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(DiseaseReportActivity.this);
		report = (Button) findViewById(R.id.btn_choice_people);
		choicePeople = (Button) findViewById(R.id.report);
		message = (EditText) findViewById(R.id.et_report_people);
		peopleNameTextView = (TextView) findViewById(R.id.tv_report_people);
		report.setOnClickListener(listener);
		choicePeople.setOnClickListener(listener);
		bundle = getIntent().getExtras();
		mark = bundle.getInt(BUNDLE_MARK);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btn_choice_people:
				Intent intent = new Intent();
				intent.setClass(DiseaseReportActivity.this,
						ChiocePeopleActivity.class);
				Bundle choiceBundle = new Bundle();
				choiceBundle.putInt(ChiocePeopleActivity.BUNDLE_FROM_MARK,
						ChiocePeopleActivity.BUNDLE_FROM_DISEASE);
				intent.putExtras(choiceBundle);
				startActivityForResult(intent, REQUEST_CODE_NAME);
				break;
			case R.id.report:
				if (peopleName.equals("") || peopleNameId.equals("")) {
					SystemUtils.MToast("请选择上报接收人", DiseaseReportActivity.this);
					return;
				}
				String reportMessage = message.getEditableText().toString();
				if (reportMessage.equals("")) {
					SystemUtils.MToast("请填写上报意见", DiseaseReportActivity.this);
					return;
				}
				if (mark == BUNDLE_FROM_DISEASE_DETAILS) {// 如果是从病害信息详情页面来的,只需要上传病害信息和ID以及上报人员和上报意见就尅了
					String diseaseId = bundle
							.getString(BUNDLE_VALUE_DISEASE_ID);
					RequestParams requestParams = new RequestParams();
					requestParams.add("diseaseRecord.id", diseaseId);
					requestParams.add("stepType", "next");
					requestParams.add("flowAuditInfo.receiveUserId",
							peopleNameId + "");
					requestParams.add("flowAuditInfo.receiveUserName",
							peopleName);
					requestParams.add("flowAuditInfo.idea", reportMessage);
					BaseConnectTemplet<String> baseConnectTemplet = new BaseConnectTemplet<>(
							DiseaseReportActivity.this, "提示", "正在连接服务器",
							httpConnectReciver, requestParams,
							HTTPConstant.APPROVAL_DISEASE,
							new TypeToken<UserMessage>() {
							}.getType());
					baseConnectTemplet.setProgressView();
					baseConnectTemplet.getData();
				} else if (mark == BUNDLE_FROM_DISEASE_EDIT) {// 从病害信息编辑页面来的，需要提交整个病害信息表，以及病害信息意见和审批人
					DiseaseRecord diseaseRecord = (DiseaseRecord) bundle
							.getSerializable(BUNDLE_VALUE_DISEASE_MESSAGE);
					String[] filePath = new String[diseaseRecord
							.getAttachmentUrls().size()];
					for (int i = 0; i < diseaseRecord.getAttachmentUrls()
							.size(); i++) {
						filePath[i] = diseaseRecord.getAttachmentUrls().get(i)
								.getPath();
					}
					RequestParams params = new RequestParams();
					params.put("deviceType", "momobile");
					params.put("stepType", "next");// 下一步流程
					params.put("flowAuditInfo.receiveUserId ", peopleNameId);// 审批人的ID
					params.put("flowAuditInfo.receiveUserName", peopleName);// 审批人名字
					params.put("flowAuditInfo.idea", reportMessage);// 审批人意见
					// 下面是病害信息数据
					params.put(
							"diseaseRecord.id",
							diseaseRecord.getId() == null ? "" : diseaseRecord
									.getId());// 病害信息ID
					params.put(
							"diseaseRecord.sn",
							diseaseRecord.getSn() == null ? "" : diseaseRecord
									.getSn());
					params.put("diseaseRecord.routeCode",
							diseaseRecord.getRouteCode() == null ? ""
									: diseaseRecord.getRouteCode());
					params.put("diseaseRecord.stake", diseaseRecord.getStake()
							+ "");
					params.put("diseaseRecord.diseaseLevel",
							diseaseRecord.getDiseaseLevel() == null ? ""
									: diseaseRecord.getDiseaseLevel());
					params.put("diseaseRecord.diseaseType",
							diseaseRecord.getDiseaseType() == null ? ""
									: diseaseRecord.getDiseaseType());
					params.put("diseaseRecord.diseasePosition",
							diseaseRecord.getDiseasePosition() == null ? ""
									: diseaseRecord.getDiseasePosition());
					params.put("diseaseRecord.estimatingScheme",
							diseaseRecord.getEstimatingScheme() == null ? ""
									: diseaseRecord.getEstimatingScheme());
					params.put("diseaseRecord.estimatingJob",
							diseaseRecord.getEstimatingJob() + "");
					params.put("diseaseRecord.estimatingUnit",
							diseaseRecord.getEstimatingUnit() == null ? ""
									: diseaseRecord.getEstimatingUnit());
					params.put("diseaseRecord.reportTime",
							diseaseRecord.getReportTime());
					params.put("diseaseRecord.orgName",
							diseaseRecord.getOrgName() == null ? ""
									: diseaseRecord.getOrgName());
					params.put("diseaseRecord.orgId",
							diseaseRecord.getOrgId() == null ? ""
									: diseaseRecord.getOrgId());
					params.put("diseaseRecord.reportorId",
							diseaseRecord.getReportorId() == null ? ""
									: diseaseRecord.getReportorId());
					params.put("diseaseRecord.reportorName",
							diseaseRecord.getReportorName() == null ? ""
									: diseaseRecord.getReportorName());
					params.put("diseaseRecord.reportorPhone",
							diseaseRecord.getReportorPhone() == null ? ""
									: diseaseRecord.getReportorPhone());
					params.put("diseaseRecord.remark",
							diseaseRecord.getRemark() == null ? ""
									: diseaseRecord.getRemark());
					params.put("diseaseRecord.estimatingCost",
							diseaseRecord.getEstimatingCost() + "");
					params.put("diseaseRecord.diseasePart", StringAdapter
							.getDiseaseIdByName(DiseaseReportActivity.this,
									diseaseRecord.getDiseasePart()));
					File[] files = new File[filePath.length];
					for (int i = 0; i < filePath.length; i++) {
						files[i] = new File(filePath[i]);
					}
					try {
						params.put("files", files);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					String fileNames = "";
					for (int i = 0; i < files.length; i++) {
						fileNames = fileNames + files[i].getName() + ";";
					}
					params.put("fileNames", fileNames);
					AsyncUploadFiles<String> asyncUploadFiles = new AsyncUploadFiles<>(
							DiseaseReportActivity.this, "提示", "正在上传病害信息和附件",
							httpConnectReciver, params,
							HTTPConstant.APPROVAL_DISEASE,
							new TypeToken<String>() {
							}.getType());
					asyncUploadFiles.setProgressViewCanCancel();
					try {
						asyncUploadFiles.uploadFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;
			default:
				break;
			}
		}

	};

	HttpConnectReciver<String> httpConnectReciver = new HttpConnectReciver<String>() {

		@Override
		public void onSuccess(String t, BaseEntity baseEntity) {
			int status = baseEntity.getStatus();
			String remind = "";
			if (status == 0) {
				remind = "上报失败";
			} else if (status == 1) {
				remind = "上报成功";
				finish();
			} else if (status == 2) {
				remind = "权限不足";
			} else if (status == 3) {
				remind = "未登录，或者登录过期";
			}
			SystemUtils.MToast(remind, DiseaseReportActivity.this);

		}

		@Override
		public void onFail(String string) {

		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (data == null) {
				SystemUtils.MToast("您还没选择上报人", DiseaseReportActivity.this);
				return;
			}
			Bundle bundle = data.getExtras();
			peopleName = bundle.getString(BUNDLE_NAME);
			peopleNameId = bundle.getString(BUNDLE_NAME_ID);
			peopleNameTextView.setText(peopleName);
		} else {
			SystemUtils.MToast("您没有选择上报的人", DiseaseReportActivity.this);
		}
	}
}
