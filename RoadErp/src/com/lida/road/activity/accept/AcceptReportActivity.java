package com.lida.road.activity.accept;

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
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.main.ChiocePeopleActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.utils.DataUtil;
import com.loopj.android.http.RequestParams;

public class AcceptReportActivity extends MainBaseActivity {
	public static final String BUNDLE_ACCEPT_ID = "accept_id";
	/**
	 * bundle标志
	 */
	public static final String BUNDLE_MARK = "bundle_mark";
	/**
	 * 代表施工方传值过来
	 */
	public static final int BUNDLE_FROM_CONSTRUCTION = 1;
	public static final String BUNDLE_VALUE_HDJOB = "hd_job";// 工程量差
	public static final String BUNDLE_VALUE_HDUNIT = "hd_unit";// 计量差
	public static final String BUNDLE_VALUE_HDCOST = "hd_cost";// 工程金额差
	public static final String BUNDLE_VALUE_DATE = "hd_date";
	/**
	 * 代表工务科来的
	 */
	public static final int BUNDLE_FROM_WORK_BRANCH = 2;
	public static final String BUNDLE_VALUE_REMARK = "supervision_remark";
	public static final String BUNDLE_NAME = "bundle_name";
	public static final String BUNDLE_NAME_ID = "bundle_name_id";
	public static final int REQUEST_CODE_NAME = 111;// 返回人名和ID用的startactivity的code
	private Button report, choicePeople;
	private EditText message;
	private TextView peopleNameTextView;
	private String peopleName = "";
	private String peopleNameId = "";
	private String acceptId;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept_report);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "验收任务上报");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(AcceptReportActivity.this);
		choicePeople = (Button) findViewById(R.id.btn_choice_people);
		report = (Button) findViewById(R.id.report);
		message = (EditText) findViewById(R.id.et_report_people);
		peopleNameTextView = (TextView) findViewById(R.id.tv_report_people);
		report.setOnClickListener(listener);
		choicePeople.setOnClickListener(listener);
		bundle = getIntent().getExtras();
		acceptId = bundle.getString(BUNDLE_ACCEPT_ID);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btn_choice_people:
				Intent intent = new Intent();
				intent.setClass(AcceptReportActivity.this,
						ChiocePeopleActivity.class);
				Bundle choiceBundle = new Bundle();
				choiceBundle.putInt(ChiocePeopleActivity.BUNDLE_FROM_MARK,
						ChiocePeopleActivity.BUNDLE_FROM_ACCEPT);
				choiceBundle.putString(
						ChiocePeopleActivity.BUNDLE_ACCEPTENCE_ID, acceptId);
				intent.putExtras(choiceBundle);
				startActivityForResult(intent, REQUEST_CODE_NAME);
				break;
			case R.id.report:
				// 这个上报要区分是工务科上报还是施工方上报，监理只需上传意见，施工方还要上传一系列的量差
				if (peopleName == null || peopleName.equals("")
						|| peopleNameId == null || peopleNameId.equals("")) {
					SystemUtils.MToast("请选择上报接收人", AcceptReportActivity.this);
					return;
				}
				RequestParams requestParams = new RequestParams();
				requestParams.add("acceptance.id", acceptId);// 添加ID
				requestParams.add("stepType", "next");// 添加步骤
				requestParams.add("flowAuditInfo.receiveUserId", peopleNameId);
				requestParams.add("flowAuditInfo.receiveUserName", peopleName);
				requestParams.add("flowAuditInfo.idea", message
						.getEditableText().toString());
				int from = bundle.getInt(BUNDLE_MARK);
				if (from == BUNDLE_FROM_CONSTRUCTION) {// 施工方
					requestParams.add("acceptance.hdJob",
							bundle.getString(BUNDLE_VALUE_HDJOB));
					requestParams.add("acceptance.hdUnit",
							bundle.getString(BUNDLE_VALUE_HDUNIT));
					requestParams.add("acceptance.hdCost",
							bundle.getString(BUNDLE_VALUE_HDCOST));
					requestParams.add("acceptance.acceptanceDate",
									bundle.getString(BUNDLE_VALUE_DATE));
				} else if (from == BUNDLE_FROM_WORK_BRANCH) {// 工务科
					// 工務科不用傳送
				}
				BaseConnectTemplet<String> baseConnectTemplet = new BaseConnectTemplet<>(
						AcceptReportActivity.this, "提示", "正在连接服务器",
						httpConnectReciver, requestParams,
						HTTPConstant.APPROL_ACCEPTENCE,
						new TypeToken<String>() {
						}.getType());
				baseConnectTemplet.setProgressView();
				baseConnectTemplet.getData();
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
			if (status == 0) {
				SystemUtils.MToast("上报失败！", AcceptReportActivity.this);
			} else if (status == 1) {
				SystemUtils.MToast("上报成功", AcceptReportActivity.this);
				finish();
			} else if (status == 2) {
				SystemUtils.MToast("权限不足", AcceptReportActivity.this);
			} else if (status == 3) {
				SystemUtils
						.MToast("您未登录或登录过期，请重新登陆", AcceptReportActivity.this);
			}
			
		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("连接服务器失败！", AcceptReportActivity.this);

		}

	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (data == null) {
				SystemUtils.MToast("您还没选择上报人", AcceptReportActivity.this);
				return;
			}
			Bundle bundle = data.getExtras();
			peopleName = bundle.getString(BUNDLE_NAME);
			peopleNameId = bundle.getString(BUNDLE_NAME_ID);
			peopleNameTextView.setText(peopleName);
		} else {
			SystemUtils.MToast("您没有选择上报的人", AcceptReportActivity.this);
		}
	}
}
