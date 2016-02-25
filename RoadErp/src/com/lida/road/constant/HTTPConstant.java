package com.lida.road.constant;

public class HTTPConstant {
	/**
	 * IP根目录
	 */
	public static String HOST_URL = "http://222.247.52.6:82/";
	/**
	 * IP地址，不带斜杆的
	 */
	public static String HOST_URL_NO_SPRIT = "http://222.247.52.6:82";

	/**
	 * 登录地址
	 */
	public static String LOGIN_URL = HOST_URL + "loginMobile.do";
	/**
	 * 获取我的病害信息
	 */
	public static String MY_DISEASE_MESSAGE_URL = HOST_URL
			+ "diseaseRecord_mList.do";
	/**
	 * 根据病害信息ID获取病害信息详情
	 */
	public static String GET_DISEASE_BY_ID = HOST_URL
			+ "diseaseRecord_mView.do";
	/**
	 * 上传我的地理信息位置
	 */
	public static String UPLOAD_MY_LOCATION = HOST_URL
			+ "baiduAction_mSetGis.do";

	/**
	 * 获取待审批的病害信息表
	 */
	public static String ACCEPT_DISEASE = HOST_URL
			+ "diseaseRecord_mAuditList.do";
	/**
	 * 获取未完成的施工单列表
	 */
	public static String ACCEPT_CONSTRUCTION = HOST_URL
			+ "construction_mAuditList.do";

	/**
	 * 获取未完成的施工单列表
	 */
	public static String ALL_CONSTRUCTION = HOST_URL + "construction_mList.do";

	/**
	 * 获取上报人员信息
	 */
	public static String GET_REPORT_PEOPLE_MESSAGE = HOST_URL
			+ "diseaseRecord_mOrgPeopleTree.do";

	/**
	 * 审批信息
	 */
	public static String APPROVAL_DISEASE = HOST_URL
			+ "diseaseRecord_mAudit.do";

	/**
	 * 保存订单
	 */
	public static String SAVE_ORDER = HOST_URL + "diseaseRecord_mSave.do";

	/**
	 * 上传图片或者视频
	 */
	public static String UPLOAD_ATTACHMENT = HOST_URL + "affix_mUpload.do";
	/**
	 * 获取待审批的验收任务
	 */
	public static String GET_WAIT_ACCEPT = HOST_URL
			+ "acceptance_mAuditList.do";
	/**
	 * 获取所有的验收任务
	 */
	public static String ALL_ACCEPT = HOST_URL + "acceptance_mList.do";
	/**
	 * 根据施工单ID获取施工详情
	 */
	public static String GET_CONSTRUCTION_BY_ID = HOST_URL
			+ "construction_mView.do";
	/**
	 * 根据施工单获取验收单详情
	 */
	public static String GET_ACCEPT_BY_ID = HOST_URL + "acceptance_mView.do";
	/**
	 * 验收单信息审批
	 */
	public static String APPROL_ACCEPTENCE = HOST_URL + "acceptance_mAudit.do";
	/**
	 * 验收单上报请求人
	 */
	public static String GET_ACCEPTENCE_REPORT_MESSAGE = HOST_URL
			+ "acceptance_mOrgPeopleTree.do";

	/**
	 * 上传施工信息
	 */
	public static String submitConstruction = HOST_URL
			+ "construction_mComplete.do";
	/**
	 * 上传监理信息
	 */
	public static String submitSupvisionConstruction = HOST_URL
			+ "construction_mSave.do";
	/**
	 * 
	 */
	public static String GET_CONSTRUCTION_DETAILS_BY_ID = HOST_URL
			+ "construction_mView.do";

	public static void resetValues() {
		/**
		 * 登录地址
		 */
		LOGIN_URL = HOST_URL + "loginMobile.do";
		/**
		 * 获取我的病害信息
		 */
		MY_DISEASE_MESSAGE_URL = HOST_URL + "diseaseRecord_mList.do";
		/**
		 * 根据病害信息ID获取病害信息详情
		 */
		GET_DISEASE_BY_ID = HOST_URL + "diseaseRecord_mView.do";
		/**
		 * 上传我的地理信息位置
		 */
		UPLOAD_MY_LOCATION = HOST_URL + "baiduAction_mSetGis.do";

		/**
		 * 获取待审批的病害信息表
		 */
		ACCEPT_DISEASE = HOST_URL + "diseaseRecord_mAuditList.do";
		/**
		 * 获取未完成的施工单列表
		 */
		ACCEPT_CONSTRUCTION = HOST_URL + "construction_mAuditList.do";

		/**
		 * 获取未完成的施工单列表
		 */
		ALL_CONSTRUCTION = HOST_URL + "construction_mList.do";

		/**
		 * 获取上报人员信息
		 */
		GET_REPORT_PEOPLE_MESSAGE = HOST_URL
				+ "diseaseRecord_mOrgPeopleTree.do";

		/**
		 * 审批信息
		 */
		APPROVAL_DISEASE = HOST_URL + "diseaseRecord_mAudit.do";

		/**
		 * 保存订单
		 */
		SAVE_ORDER = HOST_URL + "diseaseRecord_mSave.do";

		/**
		 * 上传图片或者视频
		 */
		UPLOAD_ATTACHMENT = HOST_URL + "affix_mUpload.do";
		/**
		 * 获取待审批的验收任务
		 */
		GET_WAIT_ACCEPT = HOST_URL + "acceptance_mAuditList.do";
		/**
		 * 获取所有的验收任务
		 */
		ALL_ACCEPT = HOST_URL + "acceptance_mList.do";
		/**
		 * 根据施工单ID获取施工详情
		 */
		GET_CONSTRUCTION_BY_ID = HOST_URL + "construction_mView.do";
		/**
		 * 根据施工单获取验收单详情
		 */
		GET_ACCEPT_BY_ID = HOST_URL + "acceptance_mView.do";
		/**
		 * 验收单信息审批
		 */
		APPROL_ACCEPTENCE = HOST_URL + "acceptance_mAudit.do";
		/**
		 * 验收单上报请求人
		 */
		GET_ACCEPTENCE_REPORT_MESSAGE = HOST_URL
				+ "acceptance_mOrgPeopleTree.do";

		/**
		 * 上传施工信息
		 */
		submitConstruction = HOST_URL + "construction_mComplete.do";
		/**
		 * 上传监理信息
		 */
		submitSupvisionConstruction = HOST_URL + "construction_mSave.do";
		/**
	 * 
	 */
		GET_CONSTRUCTION_DETAILS_BY_ID = HOST_URL + "construction_mView.do";
	}
}
