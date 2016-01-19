package com.lida.road.activity.disease;

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
import com.lida.road.constant.UserConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.Admin;
import com.lida.road.entity.MainTainFlowContent;

public class DiseaseMessageActivity extends MainBaseActivity {
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
				ResourceConstant.ACTIONBAR_TITLE, "巡查上报信息");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(DiseaseMessageActivity.this);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 添加tab名称和图标
		indicator = getIndicatorView("等待审批记录");
		mTabHost.addTab(mTabHost.newTabSpec("accept").setIndicator(indicator),
				AcceptDiseaseListFragment.class, null);
		
		indicator = getIndicatorView("所有记录");
		mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator(indicator),
				AllDiseaseListFragment.class, null);
		Admin admin = UserConstant.getAdmin(DiseaseMessageActivity.this);
		String mainTain = admin.getMaintainPost();
		if (MainTainFlowContent.isInspector(mainTain)) {
			indicator = getIndicatorView("缓存记录");
			mTabHost.addTab(mTabHost.newTabSpec("cache")
					.setIndicator(indicator), CacheDiseaseFragment.class, null);
		}

		setAuthorityView();
	}

	/**
	 * 根据用户的职责，判断能够增加巡查事件
	 */
	private void setAuthorityView() {
		String authority = UserConstant.getAdmin(DiseaseMessageActivity.this)
				.getMaintainPost();
		TextView textView = (TextView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_RIGHT_BUTTON,
				ResourceConstant.ACTIONBAR_RIGHT_VIEW);
		textView.setText("添加巡查记录");
		textView.setOnClickListener(listener);
		if (MainTainFlowContent.isInspector(authority)) {
			textView.setVisibility(View.VISIBLE);
		} else {
			textView.setVisibility(View.GONE);
		}

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
			case ViewIdConstant.ACTIONBAR_RIGHT_BUTTON:
				SystemUtils.intentToAnotherActivity(
						DiseaseMessageActivity.this,
						AddDeseaMessageActivity.class);
				break;
			default:
				break;
			}

		}

	};

}
