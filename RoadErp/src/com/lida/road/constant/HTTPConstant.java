package com.lida.road.constant;

public class HTTPConstant {
	/**
	 * IP根目录
	 */
	public static final String HOST_URL = "http://120.24.234.207:5880/";
	/**
	 * 登录地址
	 */
	public static final String LOGIN_URL = HOST_URL + "loginMobile.do";
	/**
	 * 获取我的病害信息
	 */
	public static final String MY_DISEASE_MESSAGE_URL = HOST_URL
			+ "diseaseRecord_mList.do";
	/**
	 * 上传我的地理信息位置
	 */
	public static final String UPLOAD_MY_LOCATION = HOST_URL+"baiduAction_mSetGis.do";
}
