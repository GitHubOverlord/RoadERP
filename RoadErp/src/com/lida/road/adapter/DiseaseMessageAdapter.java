package com.lida.road.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lida.road.R;
import com.lida.road.entity.DiseaseRecord;

public class DiseaseMessageAdapter extends BaseAdapter {
	private Context context;
	private List<DiseaseRecord> list;
	private LayoutInflater layoutInflater;

	public DiseaseMessageAdapter(Context c, List<DiseaseRecord> list) {
		this.context = c;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView orderNumber, date, stakeNumber, approvedStatus;
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
		DiseaseRecord diseaseRecord = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.listview_disease_message, parent, false);
			viewHolder.orderNumber = (TextView) convertView
					.findViewById(R.id.disease_number);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.disease_date);
			viewHolder.stakeNumber = (TextView) convertView
					.findViewById(R.id.disease_stake);
			viewHolder.approvedStatus = (TextView) convertView
					.findViewById(R.id.disease_approved);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.orderNumber
				.setText("单号：" + diseaseRecord.getId() == null ? ""
						: diseaseRecord.getId());
		viewHolder.stakeNumber.setText("桩号：" + diseaseRecord.getStake());
		viewHolder.approvedStatus.setText("审批状态"
				+ diseaseRecord.getFlowStatusName() == null ? ""
				: diseaseRecord.getFlowStatusName());
		return convertView;
	}
}
