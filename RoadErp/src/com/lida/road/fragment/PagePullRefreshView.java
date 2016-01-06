package com.lida.road.fragment;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.IBase.IPullRefreshListTemplet;
import com.jun.android_frame.http.utils.HttpConnectByJson;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.frame.R;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.entity.BasePagerEntity;
import com.lida.road.entity.Pager;
import com.loopj.android.http.RequestParams;

public class PagePullRefreshView<T> implements
		IPullRefreshListTemplet {
	private PullToRefreshListView pullToRefreshListView;
	private BaseAdapter adapter;
	private Context c;
	private String url;
	private RequestParams requestParams;
	private Type type;
	private List<T> list;
	private int page;
	public PagePullRefreshView(
			PullToRefreshListView pullToRefreshListView, BaseAdapter adapter,
			Context c, String url, RequestParams requestParams, Type type,
			List<T> list) {
		this.pullToRefreshListView = pullToRefreshListView;
		this.adapter = adapter;
		this.c = c;
		this.url = url;
		this.requestParams = requestParams;
		this.type = type;
		this.list = list;
	}
	public void start(){
		setView();
	}
	@Override
	public void setView() {
		pullToRefreshListView.setRefreshing(true);
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setMode(Mode.BOTH);
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
				c.getResources().getString(R.string.pull_to_load));
		pullToRefreshListView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel(
						c.getResources().getString(R.string.loading));
		pullToRefreshListView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel(
						c.getResources().getString(R.string.release_to_load));
		
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						SystemUtils.MToast("xxxx", c);
						if (pullToRefreshListView.isHeaderShowing()) {
							pullFromHead();
							SystemUtils.MToast("pull from head", c);
						} else if (pullToRefreshListView.isFootShowing()) {
							pullFromFoot();
							SystemUtils.MToast("pull from foot", c);
						}

					}
				});
	}

	@Override
	public void pullFromHead() {
		list.clear();
		adapter.notifyDataSetChanged();
		page = 1;
		getData(page);
	}

	@Override
	public void pullFromFoot() {
		getData(++page);
	}

	@Override
	public void getData(final int page) {
		requestParams.add("pager.currentPage", page+"");
		HttpConnectByJson<BasePagerEntity<T>> httpConnectByJson = new HttpConnectByJson<BasePagerEntity<T>>(
				new HttpConnectReciver<BasePagerEntity<T>>() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(BasePagerEntity<T> t, BaseEntity baseEntity) {
						Pager pager = t.getPager();
						if (pager.getCurrentPage() == page) {//拿到的页面和当前页面一样
							SystemUtils.MToast("没有更多数据", c);
							return;
						}
						pullToRefreshListView.onRefreshComplete();
						list.add((T) t.getValue());
						adapter.notifyDataSetChanged();
						
					}

					@Override
					public void onFail(String string) {
						
						pullToRefreshListView.onRefreshComplete();
						adapter.notifyDataSetChanged();
					}
				}, url, requestParams, type,c);
		httpConnectByJson.excute();
	}

}
