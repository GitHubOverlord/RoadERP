package com.lida.road.activity.accept;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.disease.AcceptDiseaseListFragment;
import com.lida.road.activity.disease.AllDiseaseListFragment;
import com.lida.road.constant.ViewIdConstant;

public class CheckAndAcceptDutyActivity extends MainBaseActivity {
	private FragmentTabHost mTabHost = null;;
	private View indicator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_message);
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
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 添加tab名称和图标
		indicator = getIndicatorView("等待验收");
		mTabHost.addTab(mTabHost.newTabSpec("accept").setIndicator(indicator),
				WaitToAcceptFragment.class, null);

		indicator = getIndicatorView("所有记录");
		mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator(indicator),
				AllAcceptFragment.class, null);
	}

	private View getIndicatorView(String name) {
		View v = getLayoutInflater().inflate(R.layout.include_tab_head, null);
		TextView button = (TextView) v.findViewById(R.id.tv_tab_head);
		button.setText(name);
		return v;
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				
			default:
				break;
			}

		}

	};

}
