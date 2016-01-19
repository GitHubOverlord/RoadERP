package com.lida.road.activity.accept;

import java.io.Serializable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.lida.road.entity.Acceptance;
import com.lida.road.entity.AcceptanceAndAttachement;
import com.lida.road.entity.Admin;
import com.lida.road.entity.MainTainFlowContent;
import com.lida.road.fragment.ConstructionMessageFragment;
import com.lida.road.fragment.DiseaseMessageFragment;
import com.loopj.android.http.RequestParams;

public class AcceptDetailsActivity extends MainBaseActivity {
	/**
	 * 验收详情单
	 */
	private EditText workBranchRemarkEditText, workManagerRemarkEditText,
			hdJobEditText, hdUnitEditText, hdCostEditText;
	private DatePicker datePicker;
	private Button reportBtn, backBtn, overBtn;
	private LinearLayout constructionLayout, workBranchRemarkLayout,
			workManagerRemarkLayout;
	public static final String BUNDLE_ACCPET_MESSAGE = "bundle_accept_message";
	private Acceptance acceptance;
	private Admin admin;
	private DiseaseMessageFragment diseaseMessageFragment;
	private ConstructionMessageFragment constructionMessageFragment;
	private TextView supervisionMessageTextView;
	/**
	 * 从哪个界面来的标志，如果是从详情来的，就只显示
	 */
	public static final String BUNDLE_MARK = "bundle_mark";
	public static final int BUNDLE_FROM_ACCEPT_REPORT = 1;
	public static final int BUNDLE_FROM_REPORT_DETAILS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accpet_details);
		initView();
		setAuthorityJudgeView();
	}

	/**
	 * 根据权限显示的内容 流程 工务科未上报-->工务科上报-->主管领导审批 能做的操作 工务科-->工务科
	 */
	private void setAuthorityJudgeView() {
		Bundle bundle = getIntent().getExtras();
		int mark = bundle.getInt(BUNDLE_MARK);
		if (mark == BUNDLE_FROM_REPORT_DETAILS) {// 从详情页面来的,所有的填写按钮都不可以编辑
			hdCostEditText.setEnabled(false);
			hdJobEditText.setEnabled(false);
			hdUnitEditText.setEnabled(false);
			workBranchRemarkEditText.setEnabled(false);
			workManagerRemarkEditText.setEnabled(false);
			reportBtn.setVisibility(View.GONE);
			backBtn.setVisibility(View.GONE);
			overBtn.setVisibility(View.GONE);
		} else {
			String authority = admin.getMaintainPost();
			String flowStatus = acceptance.getFlowStatus();
			if (authority.equals(MainTainFlowContent.ROLE_CONSTRUCTION)) {// 如果是施工人员,只能点上报和退回
				reportBtn.setVisibility(View.VISIBLE);
				overBtn.setVisibility(View.GONE);
				backBtn.setVisibility(View.GONE);
				workBranchRemarkLayout.setVisibility(View.GONE);
				workManagerRemarkLayout.setVisibility(View.GONE);
				// 判断当前流程，如果不是施工人员未上报状态，那么我们就设置上报按钮为消失状态,其他的內容都不可以編輯
				if (!(flowStatus
						.equals(MainTainFlowContent.ACCEPTANCE_FLOWSTATUS_NOREPORT))) {
					reportBtn.setVisibility(View.GONE);
				}
			} else if (authority
					.equals(MainTainFlowContent.ROLE_COUNTY_WORK_MANAGER)) {// 如果是县局主管领导，显示结束和退回
				reportBtn.setVisibility(View.GONE);
				hdJobEditText.setEnabled(false);
				hdUnitEditText.setEnabled(false);
				hdCostEditText.setEnabled(false);
				workBranchRemarkEditText.setEnabled(false);
				if (!(flowStatus
						.equals(MainTainFlowContent.ACCEPTANCE_FLOWSTATUS_WORK_MASTER))) {// 如果当前状态不是主管审批，那么我们就让上报退回按钮变消失状态
					overBtn.setVisibility(View.GONE);
					backBtn.setVisibility(View.GONE);

				}

			} else if (authority// 县局工务科
					.equals(MainTainFlowContent.ROLE_COUNTY_WORK_BRANCH)) {
				overBtn.setVisibility(View.GONE);
				hdJobEditText.setEnabled(false);
				hdUnitEditText.setEnabled(false);
				hdCostEditText.setEnabled(false);
				workManagerRemarkLayout.setVisibility(View.GONE);
				if (!(flowStatus
						.equals(MainTainFlowContent.ACCEPTANCE_FLOWSTATUS_WORK_BRANCH))) {// 如果当前状态不是工务科审批，那么我们就让上报退回按钮变消失状态
					reportBtn.setVisibility(View.GONE);
					backBtn.setVisibility(View.GONE);
				}
			} else {// 其他用户的话，都是不可编辑或者不可上报状态
				reportBtn.setVisibility(View.GONE);
				backBtn.setVisibility(View.GONE);
				overBtn.setVisibility(View.GONE);
				hdJobEditText.setEnabled(false);
				hdUnitEditText.setEnabled(false);
				hdCostEditText.setEnabled(false);
				workBranchRemarkEditText.setEnabled(false);
				workManagerRemarkEditText.setEnabled(false);
				reportBtn.setVisibility(View.GONE);
				backBtn.setVisibility(View.GONE);
				overBtn.setVisibility(View.GONE);
			}
		}

	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "验收管理详情");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(AcceptDetailsActivity.this);
		admin = UserConstant.getAdmin(AcceptDetailsActivity.this);
		Bundle bundle = getIntent().getExtras();
		acceptance = (Acceptance) bundle.getSerializable(BUNDLE_ACCPET_MESSAGE);
		workBranchRemarkEditText = (EditText) findViewById(R.id.et_workBranchRemark);
		workManagerRemarkEditText = (EditText) findViewById(R.id.et_workManagerRemark);
		hdJobEditText = (EditText) findViewById(R.id.et_hdJob);
		hdUnitEditText = (EditText) findViewById(R.id.et_hdUnit);
		hdCostEditText = (EditText) findViewById(R.id.et_hdCost);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		reportBtn = (Button) findViewById(R.id.btn_report);
		backBtn = (Button) findViewById(R.id.btn_back);
		overBtn = (Button) findViewById(R.id.btn_over);
		supervisionMessageTextView = (TextView) findViewById(R.id.tv_supervisorRemark);
		constructionLayout = (LinearLayout) findViewById(R.id.lv_construction_message);
		workBranchRemarkLayout = (LinearLayout) findViewById(R.id.lv_work_branch_remark);
		workManagerRemarkLayout = (LinearLayout) findViewById(R.id.lv_work_manager_remark);
		reportBtn.setOnClickListener(listener);
		backBtn.setOnClickListener(listener);
		overBtn.setOnClickListener(listener);
		getAcceptDetailsById();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.construction_details_spread:// 展开或者收起施工信息
				if (constructionLayout.getVisibility() == View.GONE) {
					constructionLayout.setVisibility(View.VISIBLE);
				} else {
					constructionLayout.setVisibility(View.GONE);
				}

				break;
			case R.id.btn_report:// 上报分为两种，施工方上报，工务科上报，施工方需要提交三個控件的信息，工務科只需要提交意見
				Bundle bundle = new Bundle();
				if (admin.getMaintainPost().equals(
						MainTainFlowContent.ROLE_CONSTRUCTION)) {// 施工方
					String hdJob = hdJobEditText.getEditableText().toString();
					String hdUnit = hdUnitEditText.getEditableText().toString();
					String hdCost = hdCostEditText.getEditableText().toString();
					if (hdJob.equals("") || hdUnit.equals("")
							|| hdCost.equals("")) {
						SystemUtils.MToast("请输入完整数据",
								AcceptDetailsActivity.this);
						return;
					}
					bundle.putString(AcceptReportActivity.BUNDLE_VALUE_HDJOB,
							hdJob);
					bundle.putString(AcceptReportActivity.BUNDLE_VALUE_HDUNIT,
							hdUnit);
					bundle.putString(AcceptReportActivity.BUNDLE_VALUE_HDCOST,
							hdCost);
					String dateString = datePicker.getYear() + "-"
							+ datePicker.getMonth() + "-"
							+ datePicker.getDayOfMonth();
					bundle.putString(AcceptReportActivity.BUNDLE_VALUE_DATE,
							dateString);
					bundle.putInt(AcceptReportActivity.BUNDLE_MARK,
							AcceptReportActivity.BUNDLE_FROM_CONSTRUCTION);
				} else if (admin.getMaintainPost().equals(
						MainTainFlowContent.ROLE_COUNTY_WORK_BRANCH)) {// 县局工务科
					bundle.putInt(AcceptReportActivity.BUNDLE_MARK,
							AcceptReportActivity.BUNDLE_FROM_WORK_BRANCH);
					bundle.putString(AcceptReportActivity.BUNDLE_VALUE_REMARK,
							supervisionMessageTextView.getEditableText()
									.toString());
				}
				bundle.putString(AcceptReportActivity.BUNDLE_ACCEPT_ID,
						acceptance.getId());
				SystemUtils.intentToAnotherActivity(AcceptDetailsActivity.this,
						AcceptReportActivity.class, bundle);
				break;
			case R.id.btn_back:// 退回，工务科退回，县局领导退回,施工方退回
				Bundle returnBundle = new Bundle();
				returnBundle.putInt(ReturnBackActivity.BUNDLE_MARK,
						ReturnBackActivity.BUNDLE_FROM_ACCEPT_DETAILS);
				returnBundle.putString(
						ReturnBackActivity.BUNDLE_VALUE_ACCEPT_ACCEPT_ID,
						acceptance.getId());
				SystemUtils.intentToAnotherActivity(AcceptDetailsActivity.this,
						ReturnBackActivity.class, returnBundle);
				break;
			case R.id.btn_over:// 结束，只能给县局领导操作
				RequestParams params = new RequestParams();
				params.add("acceptance.id", acceptance.getId());
				params.add("flowAuditInfo.idea", workManagerRemarkEditText
						.getEditableText().toString());
				params.add("stepType", "finish");
				BaseConnectTemplet<String> baseConnectTemplet = new BaseConnectTemplet<String>(
						AcceptDetailsActivity.this, "提示", "正在连接服务器",
						returnBackHttpConnectReciver, params,
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
	HttpConnectReciver<String> returnBackHttpConnectReciver = new HttpConnectReciver<String>() {

		@Override
		public void onSuccess(String t, BaseEntity baseEntity) {
			int status = baseEntity.getStatus();
			if (status == 0) {
				SystemUtils.MToast("结束失败", AcceptDetailsActivity.this);
			} else if (status == 1) {
				SystemUtils.MToast("结束成功", AcceptDetailsActivity.this);

			} else if (status == 2) {
				SystemUtils.MToast("您没有权限", AcceptDetailsActivity.this);
			} else if (status == 3) {
				SystemUtils.MToast("您未登录或登录过期", AcceptDetailsActivity.this);
			}

		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("请求服务器失败", AcceptDetailsActivity.this);
		}
	};

	private void getAcceptDetailsById() {
		RequestParams requestParams = new RequestParams();
		requestParams.add("acceptance.id", acceptance.getId());
		BaseConnectTemplet<AcceptanceAndAttachement> baseConnectTemplet = new BaseConnectTemplet<AcceptanceAndAttachement>(
				AcceptDetailsActivity.this, "提示", "正在获取审核单详情信息",
				getAcceptConnectReciver, requestParams,
				HTTPConstant.GET_ACCEPT_BY_ID,
				new TypeToken<AcceptanceAndAttachement>() {
				}.getType());
		baseConnectTemplet.setProgressView();
		baseConnectTemplet.getData();
	}

	private void addFragment(Fragment fragment, String tag, int id) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment, tag);
		transaction.commit();
	}

	private HttpConnectReciver<AcceptanceAndAttachement> getAcceptConnectReciver = new HttpConnectReciver<AcceptanceAndAttachement>() {

		@Override
		public void onSuccess(AcceptanceAndAttachement t, BaseEntity baseEntity) {

			if (baseEntity.getStatus() == 0) {
				SystemUtils.MToast("获取信息失败", AcceptDetailsActivity.this);
				return;
			} else if (baseEntity.getStatus() == 1) {
				acceptance = t.getAcceptance();
				hdJobEditText.setText(acceptance.getHdJob() == null ? ""
						: (acceptance.getHdJob() + ""));
				hdUnitEditText.setText(acceptance.getHdUnit() == null ? ""
						: (acceptance.getHdUnit() + ""));
				hdCostEditText.setText(acceptance.getHdCost() == null ? ""
						: (acceptance.getHdCost() + ""));
				workBranchRemarkEditText.setText(acceptance
						.getWorkBranchRemark() == null ? "" : acceptance
						.getWorkBranchRemark());
				workManagerRemarkEditText.setText(acceptance
						.getWorkManagerRemark() == null ? "" : acceptance
						.getWorkManagerRemark());
				supervisionMessageTextView.setText(t.getAcceptance()
						.getConstruction().getSupervisorRemark());
				diseaseMessageFragment = new DiseaseMessageFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						DiseaseMessageFragment.BUNDLE_DISEASE_MESSAGE, t
								.getAcceptance().getDiseaseRecord());
				bundle.putSerializable(
						DiseaseMessageFragment.BUNDLE_DISEASE_ATTACHMENT, (Serializable) t
								.getAffixConstructionList());
				diseaseMessageFragment.setArguments(bundle);
				constructionMessageFragment = new ConstructionMessageFragment(t
						.getAcceptance().getConstruction(),
						t.getAffixConstructionList(),
						t.getAffixSupervisorList());
				addFragment(constructionMessageFragment,
						ConstructionMessageFragment.TAG,
						R.id.fragment_construction_message);
				addFragment(diseaseMessageFragment, DiseaseMessageFragment.TAG,
						R.id.fragment_disease_message);
			} else if (baseEntity.getStatus() == 2) {
				SystemUtils.MToast("权限不足", AcceptDetailsActivity.this);
			}
		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("获取信息失败", AcceptDetailsActivity.this);

		}
	};
}
