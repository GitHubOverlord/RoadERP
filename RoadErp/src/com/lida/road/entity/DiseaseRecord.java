package com.lida.road.entity;

import java.io.Serializable;

/***
 * 日常养护-病害信息单
 * @author linniantai
 *
 */
public class DiseaseRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String Upload_Dir = "/diseaseRecord";//日常养护的病害信息的图片放这里
	/**id*/
	private String id;
	/**路线编号*/
	private String sn;
	/**路线编号*/
	private String routeCode;
	/**桩号*/
	private Double stake;
	/**病害等级*/
	private String diseaseLevel;
	/**病害部位*/
	private String diseasePart;
	/**病害类型*/
	private String diseaseType;
	/**病害位置*/
	private String diseasePosition;
	/**建议维修方案*/
	private String estimatingScheme;
	/**估算工程量*/
	private Double estimatingJob;
	/**计量单位*/
	private String estimatingUnit;
	/**估算金额（元）*/
	private Double estimatingCost;
	/**上报时间*/
	private String reportTime;
	/**上报单位*/
	private String orgName;
	/**上报单位id*/
	private String orgId;
	/**上报人员id*/
	private String reportorId;
	/**上报人员*/
	private String reportorName;
	/**上报人员电话*/
	private String reportorPhone;
	/**任务来源*/
	private String missionFrom;
	/**任务来源-中文名*/
	private String missionFromName;
	/**描述*/
	private String remark;
	/**审核状态*/
	private String flowStatus;
	/**审核状态-环节的名称*/
	private String flowStatusName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public Double getStake() {
		return stake;
	}
	public void setStake(Double stake) {
		this.stake = stake;
	}
	public String getDiseaseLevel() {
		return diseaseLevel;
	}
	public void setDiseaseLevel(String diseaseLevel) {
		this.diseaseLevel = diseaseLevel;
	}
	public String getDiseasePart() {
		return diseasePart;
	}
	public void setDiseasePart(String diseasePart) {
		this.diseasePart = diseasePart;
	}
	public String getDiseaseType() {
		return diseaseType;
	}
	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}
	public String getDiseasePosition() {
		return diseasePosition;
	}
	public void setDiseasePosition(String diseasePosition) {
		this.diseasePosition = diseasePosition;
	}
	
	public Double getEstimatingJob() {
		return estimatingJob;
	}
	public void setEstimatingJob(Double estimatingJob) {
		this.estimatingJob = estimatingJob;
	}
	public String getEstimatingUnit() {
		return estimatingUnit;
	}
	public void setEstimatingUnit(String estimatingUnit) {
		this.estimatingUnit = estimatingUnit;
	}
	public Double getEstimatingCost() {
		return estimatingCost;
	}
	public void setEstimatingCost(Double estimatingCost) {
		this.estimatingCost = estimatingCost;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getReportorId() {
		return reportorId;
	}
	public void setReportorId(String reportorId) {
		this.reportorId = reportorId;
	}
	public String getReportorName() {
		return reportorName;
	}
	public void setReportorName(String reportorName) {
		this.reportorName = reportorName;
	}
	public String getReportorPhone() {
		return reportorPhone;
	}
	public void setReportorPhone(String reportorPhone) {
		this.reportorPhone = reportorPhone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEstimatingScheme() {
		return estimatingScheme;
	}
	public void setEstimatingScheme(String estimatingScheme) {
		this.estimatingScheme = estimatingScheme;
	}
	public String getMissionFrom() {
		return missionFrom;
	}
	public void setMissionFrom(String missionFrom) {
		this.missionFrom = missionFrom;
	}
	public String getFlowStatusName() {
		return MainTainFlowContent.getDiseaseFlowStatusNameByStr(flowStatus);
	}
	public void setFlowStatusName(String flowStatusName) {
		this.flowStatusName = flowStatusName;
	}
	public String getMissionFromName() {
		if (MainTainFlowContent.DISEASE_FROM_CITY.equals(missionFrom)){
			return "市局";
		} else if (MainTainFlowContent.DISEASE_FROM_COUNTY.equals(missionFrom)){
			return "县工务科";
		} else if (MainTainFlowContent.DISEASE_FROM_STATION.equals(missionFrom)){
			return "站上报";
		} else {
			return "未知";
		}
	}
	public void setMissionFromName(String missionFromName) {
		this.missionFromName = missionFromName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	
}
