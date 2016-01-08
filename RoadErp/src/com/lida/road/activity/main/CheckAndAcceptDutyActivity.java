package com.lida.road.activity.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;

public class CheckAndAcceptDutyActivity extends MainBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_and_accept);
		initView();
	}
	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "验收任务");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(CheckAndAcceptDutyActivity.this);
		TextView textView = (TextView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_RIGHT_BUTTON,
				ResourceConstant.ACTIONBAR_RIGHT_VIEW);
		textView.setText("添加");
		textView.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case ViewIdConstant.ACTIONBAR_RIGHT_BUTTON:
				SystemUtils.intentToAnotherActivity(
						CheckAndAcceptDutyActivity.this,
						AddDeseaMessageActivity.class);
				break;

			default:
				break;
			}

		}

	};
}
