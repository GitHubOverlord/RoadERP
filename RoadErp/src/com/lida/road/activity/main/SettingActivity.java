package com.lida.road.activity.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.utils.PersistenceManager;

/**
 * 保存服务器地址和端口号的页面
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends MainBaseActivity {
	private Button save;
	private EditText ipEditText, portEditText;
	private String ip, port;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "设置");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(SettingActivity.this);
		ipEditText = (EditText) findViewById(R.id.setting_ip);
		portEditText = (EditText) findViewById(R.id.setting_port);
		ipEditText.setText(PersistenceManager.getInstance(SettingActivity.this)
				.getHostIp());
		portEditText.setText(PersistenceManager.getInstance(
				SettingActivity.this).getHostPort());
		save = (Button) findViewById(R.id.setting_keep);
		save.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.setting_keep:
				ip = ipEditText.getEditableText().toString();
				port = portEditText.getEditableText().toString();
				PersistenceManager.getInstance(SettingActivity.this).putHostIp(
						ip);
				PersistenceManager.getInstance(SettingActivity.this)
						.putHostPort(port);
				SystemUtils.MToast("保存成功", SettingActivity.this);
				break;

			default:
				break;
			}

		}
	};
}
