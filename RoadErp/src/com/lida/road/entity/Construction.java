package com.lida.road.entity;

import java.io.Serializable;
import java.util.Date;

public class Construction implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	/**对应的病害信息*/
	private DiseaseRecord diseaseRecord;
	/**施工单编号*/
	private String sn;
	/**核定维修方案*/
	private String hdMaintainScheme;
	/**核定工程量*/
	private Double hdJob;
	/**核定计量单位*/
	private String hdUnit;
	/**核定金额（元）*/
	private Double hdCost;
	/**领导审核维修方案*/
	private String ldMaintainScheme;
	/**领导审核工程量	*/
	private Double ldJob;
	/**领导审核单位*/
	private String ldUnit;
	/**领导审核金额*/
	private Double ldCost;
	/**发布时间*/
	private String publishDate;
	/**执行时间*/
	private String executeDate;
	/**完成时间*/
	private String completeDate;
	/**质保期（天）*/
	private Integer guaranteePeriod;
	/**施工单位人员-id*/
	private String constructionId;
	/**施工单位人员-名称*/
	private String constructionName;
	/**施工单位的机构id*/
	private String constructionOrgId;
	/**施工单位意见*/
	private String constructionRemark;
	/**监理单位-id*/
	private String supervisorId;
	/**监理单位-名称*/
	private String supervisorName;
	/**监理单位-机构id*/
	private String supervisorOrgId;
	/**监理单位验收意见*/
	private String supervisorRemark;
	/**备注*/
	private String remark;
	/**状态*/
	private String flowStatus;
	/**状态-名称，用户列表显示*/
	private String flowStatusName;
	/**下达的单位*/
	private String issuedOrgId;
	/**下达的人员*/
	private String issuedUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DiseaseRecord getDiseaseRecord() {
		return diseaseRecord;
	}
	public void setDiseaseRecord(DiseaseRecord diseaseRecord) {
		this.diseaseRecord = diseaseRecord;
	}
	
	public String getHdMaintainScheme() {
		return hdMaintainScheme;
	}
	public void setHdMaintainScheme(String hdMaintainScheme) {
		this.hdMaintainScheme = hdMaintainScheme;
	}
	public Double getHdJob() {
		return hdJob;
	}
	public void setHdJob(Double hdJob) {
		this.hdJob = hdJob;
	}
	public String getHdUnit() {
		return hdUnit;
	}
	public void setHdUnit(String hdUnit) {
		this.hdUnit = hdUnit;
	}
	public Double getHdCost() {
		return hdCost;
	}
	public void setHdCost(Double hdCost) {
		this.hdCost = hdCost;
	}
	public String getLdMaintainScheme() {
		return ldMaintainScheme;
	}
	public void setLdMaintainScheme(String ldMaintainScheme) {
		this.ldMaintainScheme = ldMaintainScheme;
	}
	public Double getLdJob() {
		return ldJob;
	}
	public void setLdJob(Double ldJob) {
		this.ldJob = ldJob;
	}
	public String getLdUnit() {
		return ldUnit;
	}
	public void setLdUnit(String ldUnit) {
		this.ldUnit = ldUnit;
	}
	public Double getLdCost() {
		return ldCost;
	}
	public void setLdCost(Double ldCost) {
		this.ldCost = ldCost;
	}

	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}
	public Integer getGuaranteePeriod() {
		return guaranteePeriod;
	}
	public void setGuaranteePeriod(Integer guaranteePeriod) {
		this.guaranteePeriod = guaranteePeriod;
	}
	
	public String getConstructionOrgId() {
		return constructionOrgId;
	}
	public void setConstructionOrgId(String constructionOrgId) {
		this.constructionOrgId = constructionOrgId;
	}
	public String getConstructionRemark() {
		return constructionRemark;
	}
	public void setConstructionRemark(String constructionRemark) {
		this.constructionRemark = constructionRemark;
	}
	
	public String getSupervisorRemark() {
		return supervisorRemark;
	}
	public void setSupervisorRemark(String supervisorRemark) {
		this.supervisorRemark = supervisorRemark;
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
	public String getFlowStatusName() {
		if (MainTainFlowContent.CONSTRUCTION_FLOWSTATUS_NOREPORT.equals(flowStatus)){
			return "未上报";
		} else if (MainTainFlowContent.CONSTRUCTION_FLOWSTATUS_WORK_MASTER.equals(flowStatus)){
			return "县局领导审批中";
		} else if (MainTainFlowContent.CONSTRUCTION_FLOWSTATUS_WORKING.equals(flowStatus)){
			return "施工中";
		} else if (MainTainFlowContent.CONSTRUCTION_FLOWSTATUS_WORKED.equals(flowStatus)){
			return "施工完毕";
		} else {
			return "未下达";
		}
	}
	public void setFlowStatusName(String flowStatusName) {
		this.flowStatusName = flowStatusName;
	}
	public String getIssuedOrgId() {
		return issuedOrgId;
	}
	public void setIssuedOrgId(String issuedOrgId) {
		this.issuedOrgId = issuedOrgId;
	}
	public String getConstructionId() {
		return constructionId;
	}
	public void setConstructionId(String constructionId) {
		this.constructionId = constructionId;
	}
	public String getConstructionName() {
		return constructionName;
	}
	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorOrgId() {
		return supervisorOrgId;
	}
	public void setSupervisorOrgId(String supervisorOrgId) {
		this.supervisorOrgId = supervisorOrgId;
	}
	public String getIssuedUserId() {
		return issuedUserId;
	}
	public void setIssuedUserId(String issuedUserId) {
		this.issuedUserId = issuedUserId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
