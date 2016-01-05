package com.lida.road.activity;

import android.os.Bundle;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;

public class SettingActivity extends MainBaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "登录");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(SettingActivity.this);
	}
	
}
