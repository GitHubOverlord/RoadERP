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
import com.jun.android_frame.http.base.BaseConnectTemplet;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.UserConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.UserMessage;
import com.lida.road.utils.PersistenceManager;
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

	/**
	 * 这个方法用于初始化界面
	 */
	private void initView() {
		/**
		 * 这几行代码用来设置页面头部的布局，就是页面的提示信息，以及页面返回按钮
		 */
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "登录");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setVisibility(View.GONE);
		/**
		 * 根据布局页面找到控件
		 */
		userNameEditText = (EditText) findViewById(R.id.login_user_name);
		passwordEditText = (EditText) findViewById(R.id.login_password);
		userNameEditText.setText(PersistenceManager.getInstance(
				LoginActivity.this).getUserName());
		passwordEditText.setText(PersistenceManager.getInstance(
				LoginActivity.this).getPassword());
		loginBtn = (Button) findViewById(R.id.login_login);
		settingButton = (Button) findViewById(R.id.login_setting);
		/**
		 * 设置监听事件
		 */
		settingButton.setOnClickListener(listener);
		loginBtn.setOnClickListener(listener);
		UserConstant.admin = null;
		UserConstant.diseaseCategories = null;
		UserConstant.diseaseTypes = null;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String host = PersistenceManager.getInstance(LoginActivity.this)
				.getHostIp();
		String port = PersistenceManager.getInstance(LoginActivity.this)
				.getHostPort();
		if (host != null && port != null && !host.equals("")
				&& !port.equals("")) {
			HTTPConstant.HOST_URL_NO_SPRIT = "http://" + host + ":" + port;
			HTTPConstant.HOST_URL = HTTPConstant.HOST_URL_NO_SPRIT + "/";
			System.out.println("Ip被设置为:" + HTTPConstant.HOST_URL + "--"
					+ HTTPConstant.HOST_URL_NO_SPRIT);
			HTTPConstant.resetValues();
		}

		// else {
		// HTTPConstant.HOST_URL = null;
		// HTTPConstant.HOST_URL_NO_SPRIT = null;
		// }
	}

	/**
	 * 监听事件
	 */
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**
			 * 这里是登录请求的事件
			 */
			case R.id.login_login:// 登录按钮事件执行
				if (HTTPConstant.HOST_URL == null) {
					SystemUtils.MToast("请设置Ip地址和端口号", LoginActivity.this);
					return;
				}
				userNameString = userNameEditText.getEditableText().toString();
				passwordString = passwordEditText.getEditableText().toString();
				if (userNameString.equals("") || passwordString.equals("")) {
					SystemUtils.MToast("请输入完整的账号密码", LoginActivity.this);
					return;
				}
				/**
				 * 这里是正式的HTTP请求了
				 */
				RequestParams requestParams = new RequestParams();// 设置请求参数
				requestParams.add("admin.username", userNameString);
				requestParams.add("admin.password", passwordString);
				/**
				 * 通过这个BaseConnectTemplet，这是一个加载数据的公共类，他在加载的时候，会弹出一个对话框
				 */
				BaseConnectTemplet<UserMessage> baseConnectTemplet = new BaseConnectTemplet<>(
						LoginActivity.this, "提示", "正在登陆", httpConnectReciver,
						requestParams, HTTPConstant.LOGIN_URL,
						new TypeToken<UserMessage>() {
						}.getType());
				baseConnectTemplet.setProgressViewCanCancel();
				baseConnectTemplet.getData();
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
	/**
	 * 这里是请求的回掉函数
	 */
	HttpConnectReciver<UserMessage> httpConnectReciver = new HttpConnectReciver<UserMessage>() {

		@Override
		public void onSuccess(UserMessage t,
				com.jun.android_frame.entity.BaseEntity baseEntity) {
			if (baseEntity.getStatus() == 1) {// 成功
				PersistenceManager.getInstance(LoginActivity.this).putAdmin(
						t.getAdmin());
				PersistenceManager.getInstance(LoginActivity.this)
						.putDiseaseCatogory(t.getDiseaseCategoryList());
				PersistenceManager.getInstance(LoginActivity.this)
						.putDiseaseType(t.getDiseaseTypeList());
				PersistenceManager.getInstance(LoginActivity.this).putUserName(
						userNameString);
				PersistenceManager.getInstance(LoginActivity.this).putPassorw(
						passwordString);
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
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

}
