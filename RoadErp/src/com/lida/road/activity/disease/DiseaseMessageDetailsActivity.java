package com.lida.road.activity.disease;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.lida.road.activity.main.ReturnBackActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.UserConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.AffixFile;
import com.lida.road.entity.AttachmentAndDisease;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.entity.MainTainFlowContent;
import com.lida.road.fragment.AttachmentFragment;
import com.lida.road.fragment.ImgGridViewFragment;
import com.lida.road.utils.StringAdapter;
import com.loopj.android.http.RequestParams;

/**
 * 病害信息详情页面，这个页面是不可以编辑的页面
 * 
 * @author Administrator
 * 
 */
public class DiseaseMessageDetailsActivity extends MainBaseActivity {
	public static final String BUNDLE_FROM_MARK = "bundle_from";
	public static final int BUNDLE_FROM_ALL = 1;
	public static final String BUNDLE_DISEASE_MESSAGE = "bundle_disease_message";
	public static final String BUNDLE_DISEASE_ATTACHMENT = "bundle_disease_attachment";
	private TextView diseaseNumber, routeNumber, stakeNumber, diseaseLevel,
			diseaseCatogory, diseaseType, diseasePosition, suggestFix,
			workDate, measurementUnit, estimatedAmount, reportTime,
			reportPeople, reportEnterpris, phoneNumber;
	private Button report, returnBack, over;
	private DiseaseRecord diseaseRecord;
	private List<AffixFile> attachments;
	private AttachmentAndDisease attachmentAndDisease;
	private ImgGridViewFragment imgGridViewFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_details);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "巡查记录详情");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(DiseaseMessageDetailsActivity.this);
		diseaseNumber = (TextView) findViewById(R.id.disease_number);
		routeNumber = (TextView) findViewById(R.id.route_number);
		stakeNumber = (TextView) findViewById(R.id.stake_number);
		diseaseLevel = (TextView) findViewById(R.id.disease_level);
		diseaseCatogory = (TextView) findViewById(R.id.disease_catogory);
		diseasePosition = (TextView) findViewById(R.id.disease_position);
		diseaseType = (TextView) findViewById(R.id.disease_type);
		suggestFix = (TextView) findViewById(R.id.suggest_fix);
		workDate = (TextView) findViewById(R.id.work_date);
		measurementUnit = (TextView) findViewById(R.id.measurement_unit);
		estimatedAmount = (TextView) findViewById(R.id.estimated_amount);
		reportTime = (TextView) findViewById(R.id.report_time);
		reportPeople = (TextView) findViewById(R.id.report_people);
		reportEnterpris = (TextView) findViewById(R.id.report_enterpris);
		phoneNumber = (TextView) findViewById(R.id.report_number);
		report = (Button) findViewById(R.id.btn_report);
		returnBack = (Button) findViewById(R.id.return_back);
		over = (Button) findViewById(R.id.over);
		report.setOnClickListener(listener);
		returnBack.setOnClickListener(listener);
		over.setOnClickListener(listener);
		Bundle bundle = getIntent().getExtras();
		attachmentAndDisease = (AttachmentAndDisease) bundle
				.getSerializable(BUNDLE_DISEASE_MESSAGE);
		diseaseRecord = attachmentAndDisease.getDiseaseRecord();
		attachments = attachmentAndDisease.getAffixList();
		imgGridViewFragment = new ImgGridViewFragment();
		Bundle attachmentBundle = new Bundle();
		attachmentBundle.putSerializable(ImgGridViewFragment.BUNDLE_IMG_URL,
				(Serializable) attachments);
		imgGridViewFragment.setArguments(attachmentBundle);
		addFragment(imgGridViewFragment, AttachmentFragment.TAG);
		setDateToView(diseaseRecord);
		setAuthorityView();
	}

	/**
	 * 根据权限显示下侧按钮
	 */
	private void setAuthorityView() {
		String authority = UserConstant.getAdmin(
				DiseaseMessageDetailsActivity.this).getMaintainPost();
		if (MainTainFlowContent.isInspector(authority)) {// 如果是巡查员，那么我们就不显示任何按钮
			report.setVisibility(View.GONE);
			returnBack.setVisibility(View.GONE);
			over.setVisibility(View.GONE);
		} else if (authority
				.equals(MainTainFlowContent.ROLE_COUNTY_WORK_MANAGER)) {// 如果是县局主管领导，那么我们就显示退回和结束
			report.setVisibility(View.GONE);
		} else {// 其他人，我们显示退回和上报
			over.setVisibility(View.GONE);
		}
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {// 如果是从全部页面跳转过来的，那么就只能看，不能操作
			int from = bundle.getInt(BUNDLE_FROM_MARK);
			if (from == 1) {
				report.setVisibility(View.GONE);
				returnBack.setVisibility(View.GONE);
				over.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 增加一个fragment的方法
	 * 
	 * @param fragment
	 * @param tag
	 */
	private void addFragment(Fragment fragment, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.fragment_attendance, fragment, tag);
		transaction.commit();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btn_report:
				Intent intent = new Intent();
				intent.setClass(DiseaseMessageDetailsActivity.this,
						DiseaseReportActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(DiseaseReportActivity.BUNDLE_MARK,
						DiseaseReportActivity.BUNDLE_FROM_DISEASE_DETAILS);
				bundle.putString(DiseaseReportActivity.BUNDLE_VALUE_DISEASE_ID,
						diseaseRecord.getId());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.return_back:
				Intent intent1 = new Intent();
				intent1.setClass(DiseaseMessageDetailsActivity.this,
						ReturnBackActivity.class);
				Bundle bundle1 = new Bundle();
				bundle1.putInt(ReturnBackActivity.BUNDLE_MARK,
						ReturnBackActivity.BUNDLE_FROM_DISEASE_DETAILS);
				bundle1.putString(ReturnBackActivity.BUNDLE_VALUE_DISEASE_ID,
						diseaseRecord.getId());
				intent1.putExtras(bundle1);
				startActivity(intent1);
				break;
			case R.id.over:
				RequestParams params = new RequestParams();
				params.add("stepType", "finish");
				params.add("diseaseRecord.id", diseaseRecord.getId());
				BaseConnectTemplet<String> baseConnectTemplet = new BaseConnectTemplet<String>(
						DiseaseMessageDetailsActivity.this, "提示", "正在请求服务器",
						new HttpConnectReciver<String>() {

							@Override
							public void onSuccess(String t,
									BaseEntity baseEntity) {
								int status = baseEntity.getStatus();
								String remind = "";
								if (status == 0) {
									remind = "提交失败";
								} else if (status == 1) {
									remind = "提交成功";
								} else if (status == 2) {
									remind = "没权限";
								} else if (status == 3) {
									remind = "登陆过期";
								}
								SystemUtils.MToast(remind,
										DiseaseMessageDetailsActivity.this);
								finish();
							}

							@Override
							public void onFail(String string) {
								SystemUtils.MToast("连接服务器失败",
										DiseaseMessageDetailsActivity.this);

							}
						}, params, HTTPConstant.APPROVAL_DISEASE,
						new TypeToken<String>() {
						}.getType());
				baseConnectTemplet.setProgressViewCanCancel();
				baseConnectTemplet.getData();
				break;
			default:
				break;
			}

		}

	};

	private void setDateToView(DiseaseRecord diseaseRecord) {
		diseaseNumber.setText(diseaseRecord.getSn() == null ? ""
				: diseaseRecord.getSn());
		routeNumber.setText(diseaseRecord.getRouteCode() == null ? ""
				: diseaseRecord.getRouteCode());
		stakeNumber.setText(diseaseRecord.getStake() + "");
		diseaseLevel.setText(diseaseRecord.getDiseaseLevel() == null ? ""
				: diseaseRecord.getDiseaseLevel());
		diseaseCatogory.setText(StringAdapter.diseaseCatogoryIdToString(
				diseaseRecord.getDiseasePart(),
				DiseaseMessageDetailsActivity.this));
		diseaseType.setText(diseaseRecord.getDiseaseType() == null ? ""
				: diseaseRecord.getDiseaseType());
		diseasePosition.setText(diseaseRecord.getDiseasePosition() == null ? ""
				: diseaseRecord.getDiseasePosition());
		suggestFix.setText(diseaseRecord.getEstimatingScheme() == null ? ""
				: diseaseRecord.getEstimatingScheme());
		workDate.setText(diseaseRecord.getEstimatingJob() + "");
		measurementUnit.setText(diseaseRecord.getEstimatingUnit() == null ? ""
				: diseaseRecord.getEstimatingUnit());
		estimatedAmount.setText(diseaseRecord.getEstimatingCost() + "");
		reportTime.setText(diseaseRecord.getReportTime() == null ? ""
				: diseaseRecord.getReportTime());
		reportPeople.setText(diseaseRecord.getReportorName() == null ? ""
				: diseaseRecord.getReportorName());
		reportEnterpris.setText(diseaseRecord.getOrgName() == null ? ""
				: diseaseRecord.getOrgName());
		phoneNumber.setText(diseaseRecord.getReportorPhone() == null ? ""
				: diseaseRecord.getReportorPhone());
	}
}
