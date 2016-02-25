package com.lida.road.activity.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.utils.HttpConnectByJson;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.disease.DiseaseReportActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.PeopleListBean;
import com.loopj.android.http.RequestParams;

/**
 * 选择上报的人页面
 * 
 * @author Administrator
 * 
 */
public class ChiocePeopleActivity extends MainBaseActivity {
	ExpandableListView mainlistview = null;
	List<PeopleListBean> parent = null;
	Map<String, List<PeopleListBean>> map = null;
	public static final String BUNDLE_FROM_MARK = "bundle_from_mark";
	public static final int BUNDLE_FROM_DISEASE = 1;
	public static final int BUNDLE_FROM_ACCEPT = 2;
	public static final String BUNDLE_ACCEPTENCE_ID = "accept_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice_people);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "选择上报人");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ChiocePeopleActivity.this);
		mainlistview = (ExpandableListView) this
				.findViewById(R.id.main_expandablelistview);
		mainlistview.setGroupIndicator(null);
		Bundle bundle = getIntent().getExtras();
		int from = bundle.getInt(BUNDLE_FROM_MARK);
		if (from == BUNDLE_FROM_DISEASE) {// 从病害信息来的
			getData(HTTPConstant.GET_REPORT_PEOPLE_MESSAGE, new RequestParams());
		} else if (from == BUNDLE_FROM_ACCEPT) {
			RequestParams requestParams = new RequestParams();
			requestParams.add("acceptance.id",
					bundle.getString(BUNDLE_ACCEPTENCE_ID));
			getData(HTTPConstant.GET_ACCEPTENCE_REPORT_MESSAGE, requestParams);
		}
		mainlistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				String parentId = ChiocePeopleActivity.this.parent.get(
						groupPosition).getId();
				bundle.putString(DiseaseReportActivity.BUNDLE_NAME_ID,
						map.get(parentId).get(childPosition).getId());
				bundle.putString(DiseaseReportActivity.BUNDLE_NAME,
						map.get(parentId).get(childPosition).getName());
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
				return false;
			}
		});

	}

	private void getData(String url, RequestParams requestParams) {
		HttpConnectByJson<List<PeopleListBean>> httpConnectByJson = new HttpConnectByJson<List<PeopleListBean>>(
				httpConnectReciver, url, requestParams,
				new TypeToken<List<PeopleListBean>>() {
				}.getType(), ChiocePeopleActivity.this);
		httpConnectByJson.excute();
	}

	HttpConnectReciver<List<PeopleListBean>> httpConnectReciver = new HttpConnectReciver<List<PeopleListBean>>() {

		@Override
		public void onSuccess(List<PeopleListBean> t, BaseEntity baseEntity) {
			parent = new ArrayList<>();
			map = new HashMap<>();
			for (PeopleListBean peopleListBean : t) {
				if (peopleListBean.getOrgFlag()) {// 如果是组织
					parent.add(peopleListBean);
				}
			}
			for (int i = 0; i < parent.size(); i++) {
				List<PeopleListBean> listBeans = new ArrayList<>();
				for (PeopleListBean peopleListBean2 : t) {
					if (peopleListBean2.getPid().equals(parent.get(i).getId())) {
						listBeans.add(peopleListBean2);
					}
				}
				map.put(parent.get(i).getId(), listBeans);
			}
			MyAdapter myAdapter = new MyAdapter();
			mainlistview.setAdapter(myAdapter);
			for (int i = 0; i < myAdapter.getGroupCount(); i++) {

				mainlistview.expandGroup(i);

			}
		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("连接服务器失败", ChiocePeopleActivity.this);

		}
	};

	class MyAdapter extends BaseExpandableListAdapter {

		// 得到子item需要关联的数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = parent.get(groupPosition).getId();
			return (map.get(key).get(childPosition));
		}

		// 得到子item的ID
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		// 设置子item的组件
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String key = ChiocePeopleActivity.this.parent.get(groupPosition)
					.getId();
			String info = map.get(key).get(childPosition).getName();
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) ChiocePeopleActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.expandlistview_child,
						null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.second_textview);
			tv.setText(info);
			return tv;
		}

		// 获取当前父item下的子item的个数
		@Override
		public int getChildrenCount(int groupPosition) {
			String key = parent.get(groupPosition).getId();
			int size = map.get(key).size();
			return size;
		}

		// 获取当前父item的数据
		@Override
		public Object getGroup(int groupPosition) {
			return parent.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return parent.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// 设置父item组件
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) ChiocePeopleActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.expandlistview_parent,
						null);
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.parent_textview);
			tv.setText(ChiocePeopleActivity.this.parent.get(groupPosition)
					.getName());
			Drawable drawable;
			if (isExpanded) {
				drawable = getResources().getDrawable(R.drawable.lt_open2);

			} else {
				drawable = getResources().getDrawable(R.drawable.lt_norml2);
			}
			tv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,
					null);
			return tv;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
}
