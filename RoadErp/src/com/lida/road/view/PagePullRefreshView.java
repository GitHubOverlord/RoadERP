package com.lida.road.view;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
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

public class PagePullRefreshView<T> implements IPullRefreshListTemplet,
		OnRefreshListener2<ListView> {
	private PullToRefreshListView pullToRefreshListView;
	private BaseAdapter adapter;
	private Context c;
	private String url;
	private RequestParams requestParams;
	private List<T> list;
	private int page;
	private boolean isHead = true;
	private Type type;

	public PagePullRefreshView(PullToRefreshListView pullToRefreshListView,
			BaseAdapter adapter, Context c, String url,
			RequestParams requestParams, List<T> list, Type type) {
		this.pullToRefreshListView = pullToRefreshListView;
		this.adapter = adapter;
		this.c = c;
		this.url = url;
		this.requestParams = requestParams;
		this.list = list;
		this.type = type;
	}

	public void start() {
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

		pullToRefreshListView.setOnRefreshListener(this);
		getData(1);
	}

	@Override
	public void pullFromHead() {
		isHead = true;
		list.clear();
		adapter.notifyDataSetChanged();
		page = 1;
		getData(page);
	}

	@Override
	public void pullFromFoot() {
		isHead = false;
		getData(++page);
	}

	@Override
	public void getData(final int page) {
		requestParams.add("pager.currentPage", page + "");
		HttpConnectByJson<BasePagerEntity> httpConnectByJson = new HttpConnectByJson<BasePagerEntity>(
				new HttpConnectReciver<BasePagerEntity>() {

					@Override
					public void onSuccess(BasePagerEntity t,
							BaseEntity baseEntity) {
						pullToRefreshListView.onRefreshComplete();
						Pager pager = t.getPager();
						if (!isHead && pager.getTotalPage() <= page) {// 拿到的页面和当前页面一样
							SystemUtils.MToast("没有更多数据", c);
							return;
						}
						list.addAll((Collection<? extends T>) t.getList());
						System.out.println("pullrefresh view list size:"
								+ list.size());
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String string) {

						pullToRefreshListView.onRefreshComplete();
						adapter.notifyDataSetChanged();
					}
				}, url, requestParams, type, c);
		httpConnectByJson.excute();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		pullFromHead();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		pullFromFoot();

	}

}
