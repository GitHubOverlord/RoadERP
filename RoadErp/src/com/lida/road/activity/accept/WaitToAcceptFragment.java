package com.lida.road.activity.accept;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.CheckAndAcceptAdapter;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.entity.Acceptance;
import com.lida.road.entity.BasePagerEntity;
import com.lida.road.view.PagePullRefreshView;
import com.loopj.android.http.RequestParams;

/**
 * 所有的审批记录
 * 
 * @author Administrator
 * 
 */
public class WaitToAcceptFragment extends Fragment {
	private View view;
	private List<Acceptance> list;
	private CheckAndAcceptAdapter checkAndAcceptAdapter;
	private PullToRefreshListView pullToRefreshListView;

	public WaitToAcceptFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_disease, container, false);
		initView();
		return view;

	}

	private void initView() {
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.listview_disease_list);
		list = new ArrayList<>();
		checkAndAcceptAdapter = new CheckAndAcceptAdapter(getActivity(), list);
		pullToRefreshListView.setOnItemClickListener(onItemClickListener);
		RequestParams requestParams = new RequestParams();
		PagePullRefreshView<Acceptance> pagePullRefreshView = new PagePullRefreshView<>(
				pullToRefreshListView, checkAndAcceptAdapter, getActivity(),
				HTTPConstant.GET_WAIT_ACCEPT, requestParams, list,
				new TypeToken<BasePagerEntity<Acceptance>>() {
				}.getType());
		pagePullRefreshView.start();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle = new Bundle();
			bundle.putInt(AcceptDetailsActivity.BUNDLE_MARK,AcceptDetailsActivity.BUNDLE_FROM_ACCEPT_REPORT);
			bundle.putSerializable(
					AcceptDetailsActivity.BUNDLE_ACCPET_MESSAGE,
					list.get(position - 1));
			SystemUtils.intentToAnotherActivity(getActivity(),
				AcceptDetailsActivity.class, bundle);
		}

	};
}
