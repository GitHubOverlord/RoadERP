package com.lida.road.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.DiseaseMessageAdapter;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.fragment.PagePullRefreshView;
import com.loopj.android.http.RequestParams;

public class DiseaseMessageActivity extends MainBaseActivity {
	private List<DiseaseRecord> list;
	private DiseaseMessageAdapter diseaseMessageAdapter;
	private PullToRefreshListView pullToRefreshListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disease_message);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "病害信息");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(DiseaseMessageActivity.this);
		TextView textView = (TextView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_RIGHT_BUTTON,
				ResourceConstant.ACTIONBAR_RIGHT_VIEW);
		textView.setText("添加");
		textView.setOnClickListener(listener);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_disease_list);
		list = new ArrayList<>();
		diseaseMessageAdapter = new DiseaseMessageAdapter(
				DiseaseMessageActivity.this, list);
		RequestParams requestParams = new RequestParams();
		PagePullRefreshView<DiseaseRecord> pagePullRefreshView = new PagePullRefreshView<>(
				pullToRefreshListView, diseaseMessageAdapter,
				DiseaseMessageActivity.this,
				HTTPConstant.MY_DISEASE_MESSAGE_URL, requestParams, list);
		pagePullRefreshView.start();
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
