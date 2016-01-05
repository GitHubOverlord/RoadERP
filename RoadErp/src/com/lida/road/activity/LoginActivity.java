package com.lida.road.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.Menu;
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
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.UserConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.Admin;
import com.lida.road.entity.DiseaseCategory;
import com.lida.road.entity.DiseaseType;
import com.loopj.android.http.RequestParams;

/**
 * 用户登录页面
 * 
 * @author 郑成军
 * 
 */
public class LoginActivity extends MainBaseActivity {
	private String userNameString;
	private String passwordString;
	private EditText userNameEditText, passwordEditText;
	private Button loginBtn, settingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "登录");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setVisibility(View.GONE);
		userNameEditText = (EditText) findViewById(R.id.login_user_name);
		passwordEditText = (EditText) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_login);
		settingButton = (Button) findViewById(R.id.login_setting);
		settingButton.setOnClickListener(listener);
		loginBtn.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_login:// 登录按钮事件执行
				SystemUtils.intentToAnotherActivity(LoginActivity.this,
						MainActivity.class);
//				userNameString = userNameEditText.getEditableText().toString();
//				passwordString = passwordEditText.getEditableText().toString();
//				if (userNameString.equals("") || passwordString.equals("")) {
//					SystemUtils.MToast("请输入完整的账号密码", LoginActivity.this);
//					return;
//				}
//				RequestParams requestParams = new RequestParams();
//				requestParams.add("admin.username", userNameString);
//				requestParams.add("admin.password", passwordString);
//				requestParams.add("deviceType", "mobile");
//				BaseConnectTemplet<BaseEntity> baseConnectTemplet = new BaseConnectTemplet<>(
//						LoginActivity.this, "提示", "正在登陆", httpConnectReciver,
//						requestParams, HTTPConstant.LOGIN_URL,
//						new TypeToken<BaseEntity>() {
//						}.getType());
//				baseConnectTemplet.getData();
				break;
			case R.id.login_setting:// 跳转到设置界面
				SystemUtils.intentToAnotherActivity(LoginActivity.this,
						SettingActivity.class);
				break;
			default:
				break;
			}

		}

	};
	HttpConnectReciver<BaseEntity> httpConnectReciver = new HttpConnectReciver<BaseEntity>() {

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(BaseEntity t,
				com.jun.android_frame.entity.BaseEntity baseEntity) {
			if (baseEntity.getStatus() == 1) {// 成功
				Map<String, Object> map = (Map<String, Object>) baseEntity
						.getObject();
				UserConstant.admin = (Admin) map.get("admin");
				UserConstant.diseaseCategories = (List<DiseaseCategory>) map
						.get("diseaseCategoryList");
				UserConstant.diseaseTypes = (List<DiseaseType>) map
						.get("diseaseTypeList");
				SystemUtils.intentToAnotherActivity(LoginActivity.this,
						MainActivity.class);
				finish();
			} else if (baseEntity.getStatus() == 2) {// 没权限
				SystemUtils.MToast("没有权限", LoginActivity.this);
			} else if (baseEntity.getStatus() == 0) {// 失败
				SystemUtils.MToast("登录失败", LoginActivity.this);
			}

		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("连接服务器失败！", LoginActivity.this);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
