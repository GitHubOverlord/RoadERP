package com.lida.road.constant;

public class HTTPConstant {
	/**
	 * IP根目录
	 */
	public static final String HOST_URL = "http://222.247.52.6:82/";
	/**
	 * IP地址，不带斜杆的
	 */
	public static final String HOST_URL_NO_SPRIT = "http://222.247.52.6:82";
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
	 * 根据病害信息ID获取病害信息详情
	 */
	public static final String GET_DISEASE_BY_ID = HOST_URL
			+ "diseaseRecord_mView.do";
	/**
	 * 上传我的地理信息位置
	 */
	public static final String UPLOAD_MY_LOCATION = HOST_URL
			+ "baiduAction_mSetGis.do";
	/**
	 * 获取待审批的病害信息表
	 */
	public static final String ACCEPT_DISEASE = HOST_URL
			+ "diseaseRecord_mAuditList.do";

	/**
	 * 获取未完成的施工单列表
	 */
	public static final String ACCEPT_CONSTRUCTION = HOST_URL
			+ "construction_mAuditList.do";
	/**
	 * 获取未完成的施工单列表
	 */
	public static final String ALL_CONSTRUCTION = HOST_URL
			+ "construction_mList.do";

	/**
	 * 获取上报人员信息
	 */
	public static final String GET_REPORT_PEOPLE_MESSAGE = HOST_URL
			+ "diseaseRecord_mOrgPeopleTree.do";
	/**
	 * 审批信息
	 */
	public static final String APPROVAL_DISEASE = HOST_URL
			+ "diseaseRecord_mAudit.do";
	/**
	 * 保存订单
	 */
	public static final String SAVE_ORDER = HOST_URL + "diseaseRecord_mSave.do";
	/**
	 * 上传图片或者视频
	 */
	public static final String UPLOAD_ATTACHMENT = HOST_URL
			+ "affix_mUpload.do";
	/**
	 * 获取待审批的验收任务
	 */
	public static final String GET_WAIT_ACCEPT = HOST_URL
			+ "acceptance_mAuditList.do";
	/**
	 * 获取所有的验收任务
	 */
	public static final String ALL_ACCEPT = HOST_URL + "acceptance_mList.do";
	/**
	 * 根据施工单ID获取施工详情
	 */
	public static final String GET_CONSTRUCTION_BY_ID = HOST_URL
			+ "construction_mView.do";
	/**
	 * 根据施工单获取验收单详情
	 */
	public static final String GET_ACCEPT_BY_ID = HOST_URL
			+ "acceptance_mView.do";
	/**
	 * 验收单信息审批
	 */
	public static final String APPROL_ACCEPTENCE = HOST_URL
			+ "acceptance_mAudit.do";
	/**
	 * 验收单上报请求人
	 */
	public static final String GET_ACCEPTENCE_REPORT_MESSAGE = HOST_URL
			+ "acceptance_mOrgPeopleTree.do";

	/**
	 * 上传施工信息
	 */
	public static final String submitConstruction = HOST_URL
			+ "construction_mComplete.do";
	/**
	 * 上传监理信息
	 */
	public static final String submitSupvisionConstruction = HOST_URL
			+ "construction_mSave.do";
	/**
	 * 
	 */
	public static final String GET_CONSTRUCTION_DETAILS_BY_ID  =HOST_URL+"construction_mView.do";
}
