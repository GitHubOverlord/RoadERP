package com.lida.road.activity.construction;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.fragment.AcppectConstructionFragment;
import com.lida.road.fragment.AllConstructionFragment;

public class ConstructionDutyActivity extends MainBaseActivity {
	private FragmentTabHost mTabHost = null;;
	private View indicator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_construction_duty);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "施工任务");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ConstructionDutyActivity.this);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 添加tab名称和图标
		indicator = getIndicatorView("等待审批");
		mTabHost.addTab(mTabHost.newTabSpec("myContact")
				.setIndicator(indicator), AcppectConstructionFragment.class,
				null);

		indicator = getIndicatorView("所有记录");
		mTabHost.addTab(
				mTabHost.newTabSpec("stranger").setIndicator(indicator),
				AllConstructionFragment.class, null);

	}

	private View getIndicatorView(String name) {
		View v = getLayoutInflater().inflate(R.layout.include_tab_head, null);
		Button button = (Button) v.findViewById(R.id.tv_tab_head);
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
