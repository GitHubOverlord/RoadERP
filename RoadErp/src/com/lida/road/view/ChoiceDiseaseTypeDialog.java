package com.lida.road.view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jun.android_frame.view.MyListView;
import com.lida.road.R;
import com.lida.road.constant.UserConstant;
import com.lida.road.entity.DiseaseCategory;
import com.lida.road.entity.DiseaseType;

public class ChoiceDiseaseTypeDialog {
	private LayoutInflater inflater;
	private MyListView myListView;
	private String choiceString;
	private Context c;
	private AlertDialog alertDialog;
	private View view;
	private TextView showDiseaseTypeView;
	public ChoiceDiseaseTypeDialog(Context c,TextView textView) {
		this.c = c;
		inflater = LayoutInflater.from(c);
		view = inflater.inflate(R.layout.dialog_show_disease_type_list, null);
		this.showDiseaseTypeView = textView;
	}

	public void show(String catogory,String defaultValue) {
		myListView = (MyListView) view.findViewById(R.id.dialog_disease_type);
		List<String> spinnerDiseaseTypes = new ArrayList<String>();
		for (DiseaseCategory diseaseCategory : UserConstant
				.getDiseaseCategory(c)) {
			if (diseaseCategory.getName().equals(catogory)) {
				for (DiseaseType diseaseType : UserConstant.getDiseaseType(c)) {
					if (diseaseType.getDiseaseCategoryId().equals(
							diseaseCategory.getId())) {
						spinnerDiseaseTypes.add(diseaseType.getName());
					}
				}
			}
		}
		System.out.println(spinnerDiseaseTypes.toArray().toString() + ":"
				+ spinnerDiseaseTypes.size());
		CheckListAdapter checkListAdapter = new CheckListAdapter(c,
				spinnerDiseaseTypes);
		myListView.setAdapter(checkListAdapter);
		if (defaultValue!=null && !defaultValue.equals("")) {
			String[] cacheDiseaseTypes = defaultValue.split(";");// 根据分号分割字符串
			if (cacheDiseaseTypes != null) {
				for (String string : cacheDiseaseTypes) {
					if (string == null) {
						continue;
					}
					int i = spinnerDiseaseTypes.indexOf(string);
					myListView.setItemChecked(i, true);
				}
			}
		}
		
		alertDialog = new AlertDialog.Builder(c).setTitle("请选择病害信息种类")
				.setView(view).setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String checkDiseaseType = "";
						for (int i = 0; i < myListView.getAdapter().getCount(); i++) {
							if (myListView.isItemChecked(i)) {
								checkDiseaseType = checkDiseaseType
										+ myListView.getAdapter().getItem(i)
												.toString() + ";";
							}
						}
						showDiseaseTypeView.setText(checkDiseaseType);
					}
				}).show();
	}

	public View getView() {
		return view;
	}

	public void dissmiss() {
		alertDialog.dismiss();
	}
}
