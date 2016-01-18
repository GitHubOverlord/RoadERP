package com.lida.road.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lida.road.R;
import com.lida.road.entity.Acceptance;

public class CheckAndAcceptAdapter extends BaseAdapter {
	private Context context;
	private List<Acceptance> list;
	private LayoutInflater layoutInflater;

	public CheckAndAcceptAdapter(Context c, List<Acceptance> list) {
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
		Acceptance acceptance = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.listview_acceptance,
					parent, false);
			viewHolder.orderNumber = (TextView) convertView
					.findViewById(R.id.accpentance_number);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.accpentance_date);
			viewHolder.stakeNumber = (TextView) convertView
					.findViewById(R.id.accpentance_stake);
			viewHolder.approvedStatus = (TextView) convertView
					.findViewById(R.id.accpentance_approved);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.orderNumber.setText("单号：" + (acceptance.getSn() == null ? "未填写"
				: acceptance.getSn()));
		viewHolder.stakeNumber.setText("桩号："
				+ (acceptance.getDiseaseRecord().getStake()));
		viewHolder.approvedStatus.setText("审批状态:"
				+ (acceptance.getFlowStatusName() == null ? "" : acceptance
				.getFlowStatusName()));
		viewHolder.date.setText("日期:"+(acceptance.getAcceptanceDate()==null?"未填写":acceptance.getAcceptanceDate()));
		return convertView;
	}
}
