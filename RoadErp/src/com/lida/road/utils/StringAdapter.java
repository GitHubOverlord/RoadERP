package com.lida.road.utils;

import java.util.Date;
import java.util.List;

import android.content.Context;

import com.jun.frame.utils.SystemUtils;
import com.lida.road.constant.UserConstant;
import com.lida.road.entity.DiseaseCategory;

public class StringAdapter {
	/**
	 * 把病害部位的ID转换成string
	 * @param id
	 * @param c
	 * @return
	 */
	public static String diseaseCatogoryIdToString(String id, Context c) {
		List<DiseaseCategory> list = UserConstant.getDiseaseCategory(c);
		if (list == null) {
			return "";
		}
		for (DiseaseCategory diseaseCategory : list) {
			if (diseaseCategory.getId().equals(id)) {
				return diseaseCategory.getName();
			}
		}
		return "";
	}

	public static String[] getAllDiseaseCatogries(Context c) {
		String[] spinnerDiseaseCatogoryString = new String[UserConstant
				.getDiseaseCategory(c).size()];
		for (int i = 0; i < UserConstant.getDiseaseCategory(c).size(); i++) {
			spinnerDiseaseCatogoryString[i] = UserConstant
					.getDiseaseCategory(c).get(i).getName();
		}
		return spinnerDiseaseCatogoryString;
	}

	/**
	 * 把病害部位的名字转换成Id
	 */
	public static String getDiseaseIdByName(Context c, String name) {
		if (name == null) {
			return "";
		}
		for (int i = 0; i < UserConstant.getDiseaseCategory(c).size(); i++) {
			if (UserConstant.getDiseaseCategory(c).get(i).getName()
					.equals(name)) {
				return UserConstant.getDiseaseCategory(c).get(i).getId();
			}
		}
		return "";
	}
	/**
	 * 根据路线编号生成病害编号
	 */
	public static String getDiseaseNumberByRouteNumber(String number){
		if (number == null || number.equals("")) {
			return "";
		}
		Date date = new Date();
		@SuppressWarnings("deprecation")
		String dateString = ""+date.getYear()+date.getMonth()+date.getDay()+date.getHours()+date.getMinutes()+date.getSeconds()+"";
		String string = "XC"+number+dateString;
		return string;

	}
}
