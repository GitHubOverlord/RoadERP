package com.lida.road.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.base.BaseConnectTemplet;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.accept.CheckAndAcceptDutyActivity;
import com.lida.road.activity.disease.DiseaseMessageActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.loopj.android.http.RequestParams;

/**
 * 退回的页面
 * 
 * @author Administrator
 * 
 */
public class ReturnBackActivity extends MainBaseActivity {
	private Button returnBack;
	private EditText suggest;
	public static final String BUNDLE_MARK = "bundle_mark";
	/**
	 * 从病害信息来的
	 */
	public static final int BUNDLE_FROM_DISEASE_DETAILS = 1;
	public static final String BUNDLE_VALUE_DISEASE_ID = "disease_id";
	/**
	 * 从审验单详情来的
	 */
	public static final int BUNDLE_FROM_ACCEPT_DETAILS = 2;
	public static final String BUNDLE_VALUE_ACCEPT_ACCEPT_ID = "accept_id";
	int mark = 0;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_back);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "退回");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ReturnBackActivity.this);
		returnBack = (Button) findViewById(R.id.btn_return_back);
		suggest = (EditText) findViewById(R.id.et_message);
		returnBack.setOnClickListener(listener);
		bundle = getIntent().getExtras();
		mark = bundle.getInt(BUNDLE_MARK);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			String message = suggest.getEditableText().toString();
			RequestParams requestParams = new RequestParams();
			requestParams.add("stepType", "up");// 发送下一步指令
			requestParams.add("flowAuditInfo.idea", message);
			String url = null;
			if (mark == BUNDLE_FROM_DISEASE_DETAILS) {// 从病害信息想请来的
				url = HTTPConstant.APPROVAL_DISEASE;
				String diseaseId = bundle.getString(BUNDLE_VALUE_DISEASE_ID);
				requestParams.add("diseaseRecord.id", diseaseId);

			} else if (mark == BUNDLE_FROM_ACCEPT_DETAILS) {// 从审核单详情来的
				url = HTTPConstant.APPROL_ACCEPTENCE;
				requestParams.add("acceptance.id",
						bundle.getString(BUNDLE_VALUE_ACCEPT_ACCEPT_ID));
			}
			BaseConnectTemplet<String> baseConnectTemplet = new BaseConnectTemplet<>(
					ReturnBackActivity.this, "提示", "正在请求服务器",
					httpConnectReciver, requestParams, url,
					new TypeToken<String>() {
					}.getType());
			baseConnectTemplet.setProgressView();
			baseConnectTemplet.getData();

		}

	};
	HttpConnectReciver<String> httpConnectReciver = new HttpConnectReciver<String>() {

		@Override
		public void onSuccess(String t, BaseEntity baseEntity) {
			int status = baseEntity.getStatus();
			String message = "";
			if (status == 0) {
				message = "提交失败";
			} else if (status == 1) {
				message = "提交成功";
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				if (mark == BUNDLE_FROM_ACCEPT_DETAILS) {// 从审核单来的
					intent.setClass(ReturnBackActivity.this,
							CheckAndAcceptDutyActivity.class);
				} else if (mark == BUNDLE_FROM_DISEASE_DETAILS) {// 从病害详情页面来的
					intent.setClass(ReturnBackActivity.this,
							DiseaseMessageActivity.class);
				}
				startActivity(intent);
			} else if (status == 2) {
				message = "权限不足";
			} else if (status == 3) {
				message = "登录过期";
			}
			SystemUtils.MToast(message, ReturnBackActivity.this);
		}

		@Override
		public void onFail(String string) {
			// TODO Auto-generated method stub

		}
	};
}
