package com.lida.road.constant;

import java.util.List;

import com.lida.road.entity.Admin;
import com.lida.road.entity.DiseaseCategory;
import com.lida.road.entity.DiseaseType;

public class UserConstant {
	/**
	 * 登录的时候返回的用户表信息
	 */
	public static Admin admin;
	/**
	 * 登录的时候返回的损坏记录信息
	 */
	public static List<DiseaseCategory> diseaseCategories;
	/**
	 * 登录的时候返回的损坏类型信息
	 */
	public static List<DiseaseType> diseaseTypes;
}
