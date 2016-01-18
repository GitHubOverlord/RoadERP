package com.lida.road.activity.construction;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.disease.DiseaseMessageDetailsActivity;
import com.lida.road.adapter.ConstructionMessageAdapter;
import com.lida.road.adapter.DiseaseMessageAdapter;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.UserConstant;
import com.lida.road.entity.BasePagerEntity;
import com.lida.road.entity.Construction;
import com.lida.road.entity.DiseaseRecord;
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
				HTTPConstant.ACCEPT_CONSTRUCTION, requestParams, list,
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
			if("construction".equals(UserConstant.getAdmin(getActivity()).getMaintainPost())){
				SystemUtils.intentToAnotherActivity(getActivity(),
						ConstructionDutyDetailsActivity.class, bundle);
			}else{
				SystemUtils.intentToAnotherActivity(getActivity(),
						ConstructionSupvisionDetailsActivity.class, bundle);
			}
			
		}

	};
}
