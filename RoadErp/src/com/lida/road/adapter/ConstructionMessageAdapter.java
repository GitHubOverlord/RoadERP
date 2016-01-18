package com.lida.road.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lida.road.R;
import com.lida.road.entity.Construction;
import com.lida.road.utils.DataUtil;

public class ConstructionMessageAdapter extends BaseAdapter {
	private Context context;
	private List<Construction> list;
	private LayoutInflater layoutInflater;

	public ConstructionMessageAdapter(Context c, List<Construction> list) {
		this.context = c;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView sn, publishDate, hdJob, executeDate;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Construction construction = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.listview_construction_message, parent, false);
			viewHolder.sn = (TextView) convertView
					.findViewById(R.id.construction_sn);
			viewHolder.publishDate = (TextView) convertView
					.findViewById(R.id.construction_publishDate);
			viewHolder.hdJob = (TextView) convertView
					.findViewById(R.id.construction_hdJob);
			viewHolder.executeDate = (TextView) convertView
					.findViewById(R.id.construction_executeDate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.sn
				.setText("施工单号：" + construction.getSn());
		viewHolder.publishDate.setText("发布日期：" + DataUtil.parseStringDate("yyyy-MM-dd",construction.getPublishDate()) );
		viewHolder.executeDate.setText("执行日期：" + DataUtil.parseStringDate("yyyy-MM-dd",construction.getExecuteDate()));

		viewHolder.hdJob.setText("工程量：" + construction.getHdJob());
		
		return convertView;
	}
}
