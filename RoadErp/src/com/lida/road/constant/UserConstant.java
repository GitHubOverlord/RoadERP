package com.lida.road.constant;

import java.util.List;

import android.content.Context;

import com.lida.road.entity.Admin;
import com.lida.road.entity.DiseaseCategory;
import com.lida.road.entity.DiseaseType;
import com.lida.road.utils.PersistenceManager;

public class UserConstant {
	public static Admin admin;
	public static List<DiseaseCategory> diseaseCategories;
	public static List<DiseaseType> diseaseTypes;
	/**
	 * 登录的时候返回的用户表信息
	 */
	public static Admin getAdmin(Context c){
		if (admin == null) {
			admin = PersistenceManager.getInstance(c).getAdmin();
		}
		return admin;
	}
	/**
	 * 登录的时候返回的损坏记录信息
	 */
	public static List<DiseaseCategory> getDiseaseCategory(Context c){
		if (diseaseCategories == null) {
			diseaseCategories = PersistenceManager.getInstance(c).getDiseaseCatogory();
		}
		return diseaseCategories;
	}
	/**
	 * 登录的时候返回的损坏类型信息
	 */
	public static List<DiseaseType> getDiseaseType(Context c){
		if (diseaseTypes == null) {
			diseaseTypes = PersistenceManager.getInstance(c).getDiseaseType();
		}
		return diseaseTypes;
	}
}
