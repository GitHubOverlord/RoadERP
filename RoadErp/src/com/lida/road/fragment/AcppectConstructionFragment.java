package com.lida.road.fragment;

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
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.construction.ConstructionDutyDetailsActivity;
import com.lida.road.adapter.ConstructionMessageAdapter;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.UserConstant;
import com.lida.road.entity.BasePagerEntity;
import com.lida.road.entity.Construction;
import com.lida.road.view.PagePullRefreshView;
import com.loopj.android.http.RequestParams;
/**
 * 待审批的施工頁面
 * @author Administrator
 *
 */
public class AcppectConstructionFragment extends Fragment{
	private View view;
	private List<Construction> list;
	private ConstructionMessageAdapter constructionMessageAdapter;
	private PullToRefreshListView pullToRefreshListView;
	public static final String BUNDLE_DISEASE_MESSAGE = "bundle_disease_message";
	public AcppectConstructionFragment() {
		super();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_construction, container, false);
		initView();
		return view;

	}
	private void initView() {
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.listview_construction_list);
		list = new ArrayList<>();
		constructionMessageAdapter = new ConstructionMessageAdapter(getActivity(), list);
		pullToRefreshListView.setOnItemClickListener(onItemClickListener);
		RequestParams requestParams = new RequestParams();
		//requestParams.add("flowStatus", "2");
		PagePullRefreshView<Construction> pagePullRefreshView = new PagePullRefreshView<>(
				pullToRefreshListView, constructionMessageAdapter, getActivity(),
				HTTPConstant.ALL_CONSTRUCTION, requestParams, list,
				new TypeToken<BasePagerEntity<Construction>>() {
				}.getType());
		pagePullRefreshView.start();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(BUNDLE_DISEASE_MESSAGE, list.get(position-1));
			Construction construction=list.get(position-1);
			System.out.println("aaa"+construction.getSupervisorRemark());
			if(null==construction.getSupervisorRemark()||"".equals(construction.getSupervisorRemark())){
	
				System.out.println("bbb"+construction.getSupervisorRemark());
					Toast.makeText(getActivity(), "监理还未提交意见，暂时不能进行完工操作！",

							Toast.LENGTH_LONG).show();

					return;

				
			}
			System.out.println(UserConstant.getAdmin(getActivity()).getMaintainPost());
			SystemUtils.intentToAnotherActivity(getActivity(),
					ConstructionDutyDetailsActivity.class, bundle);
		}

	};
}