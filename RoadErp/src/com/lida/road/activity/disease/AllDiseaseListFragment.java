package com.lida.road.activity.disease;

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
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.base.BaseConnectTemplet;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.DiseaseMessageAdapter;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.entity.AttachmentAndDisease;
import com.lida.road.entity.BasePagerEntity;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.view.PagePullRefreshView;
import com.loopj.android.http.RequestParams;

/**
 * 全部的病害信息列表页面
 * 
 * @author Administrator
 * 
 */
public class AllDiseaseListFragment extends Fragment {
	private View view;
	private List<DiseaseRecord> list;
	private DiseaseMessageAdapter diseaseMessageAdapter;
	private PullToRefreshListView pullToRefreshListView;

	public AllDiseaseListFragment() {
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
		diseaseMessageAdapter = new DiseaseMessageAdapter(getActivity(), list);
		pullToRefreshListView.setOnItemClickListener(onItemClickListener);
		RequestParams requestParams = new RequestParams();
		PagePullRefreshView<DiseaseRecord> pagePullRefreshView = new PagePullRefreshView<>(
				pullToRefreshListView, diseaseMessageAdapter, getActivity(),
				HTTPConstant.MY_DISEASE_MESSAGE_URL, requestParams, list,
				new TypeToken<BasePagerEntity<DiseaseRecord>>() {
				}.getType());
		pagePullRefreshView.start();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DiseaseRecord diseaseRecord = list.get(position - 1);
			RequestParams requestParams = new RequestParams();
			requestParams.add("diseaseRecord.id", diseaseRecord.getId());
			BaseConnectTemplet<AttachmentAndDisease> baseConnectTemplet = new BaseConnectTemplet<AttachmentAndDisease>(
					getActivity(), "提示", "正在获取数据", httpConnectReciver,
					requestParams, HTTPConstant.GET_DISEASE_BY_ID,
					new TypeToken<AttachmentAndDisease>() {
					}.getType());
			baseConnectTemplet.setProgressViewCanCancel();
			baseConnectTemplet.getData();
		}

	};
	HttpConnectReciver<AttachmentAndDisease> httpConnectReciver = new HttpConnectReciver<AttachmentAndDisease>() {

		@Override
		public void onSuccess(AttachmentAndDisease t, BaseEntity baseEntity) {
			/**
			 * 这里我们根据审批的状态判断是要跳转到可编辑的页面还是不可以编辑的页面
			 */
//			DiseaseRecord diseaseRecord = t.getDiseaseRecord();
//			if (diseaseRecord.getFlowStatus().equals("0") && diseaseRecord.getReportorId().equals(UserConstant.getAdmin(getActivity()).getId())) {// 如果审批状态是0，并且这条记录就是本人的，那么就是巡查人员未上报，或者上报未审批的。是可以修改的
//				Bundle bundle = new Bundle();
//				bundle.putSerializable(
//						EditDiseaseMessageActivity.BUNDLE_DISEASA_MESSAGE,
//						t);
//				SystemUtils.intentToAnotherActivity(getActivity(),
//						EditDiseaseMessageActivity.class, bundle);
//			} else {// 否则的话，我们就跳转到不能修改的界面去
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						DiseaseMessageDetailsActivity.BUNDLE_DISEASE_MESSAGE,
						t);
				bundle.putInt(DiseaseMessageDetailsActivity.BUNDLE_FROM_MARK, DiseaseMessageDetailsActivity.BUNDLE_FROM_ALL);
				SystemUtils.intentToAnotherActivity(getActivity(),
						DiseaseMessageDetailsActivity.class, bundle);
//			}

		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("获取详情信息失败！", getActivity());
		}

	};
}
