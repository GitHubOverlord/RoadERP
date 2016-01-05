package com.lida.road.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 验收单实体
 * @author linniantai
 *
 */
public class Acceptance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**验收单编号*/
	private String sn;
	/**病害信息*/
	private DiseaseRecord diseaseRecord;
	/**施工单信息*/
	private Construction construction;
	/**工程量差*/
	private Double hdJob;
	/**计量单位*/
	private Double hdUnit;
	/**工程金额差*/
	private Double hdCost;
	/**验收时间*/
	private Date acceptanceDate;
	/**工务科审核意见*/
	private String workBranchRemark;
	/**工务科审核意见*/
	private String workBranchUserId;
	/**工务科审核意见*/
	private String workBranchUserName;
	/**主管领导审核意见*/
	private String workManagerRemark;
	/**主管领导审核意见*/
	private String workManagerUserId;
	/**主管领导审核意见*/
	private String workManagerUserName;
	/**发起人用户id*/
	private String reportUserId;
	/**发起人用户Name*/
	private String reportUserName;
	/**发起人机构id*/
	private String reportOrgId;
	/***/
	private String flowStatus;
	/***/
	private String flowStatusName;
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
	
	public Double getHdJob() {
		return hdJob;
	}
	public void setHdJob(Double hdJob) {
		this.hdJob = hdJob;
	}
	public Double getHdUnit() {
		return hdUnit;
	}
	public void setHdUnit(Double hdUnit) {
		this.hdUnit = hdUnit;
	}
	public Double getHdCost() {
		return hdCost;
	}
	public void setHdCost(Double hdCost) {
		this.hdCost = hdCost;
	}
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public String getWorkBranchRemark() {
		return workBranchRemark;
	}
	public void setWorkBranchRemark(String workBranchRemark) {
		this.workBranchRemark = workBranchRemark;
	}
	public String getWorkManagerRemark() {
		return workManagerRemark;
	}
	public void setWorkManagerRemark(String workManagerRemark) {
		this.workManagerRemark = workManagerRemark;
	}
	public String getReportUserId() {
		return reportUserId;
	}
	public void setReportUserId(String reportUserId) {
		this.reportUserId = reportUserId;
	}
	public String getReportOrgId() {
		return reportOrgId;
	}
	public void setReportOrgId(String reportOrgId) {
		this.reportOrgId = reportOrgId;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getFlowStatusName() {
		return flowStatusName;
	}
	public void setFlowStatusName(String flowStatusName) {
		this.flowStatusName = flowStatusName;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getReportUserName() {
		return reportUserName;
	}
	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}
	public String getWorkBranchUserId() {
		return workBranchUserId;
	}
	public void setWorkBranchUserId(String workBranchUserId) {
		this.workBranchUserId = workBranchUserId;
	}
	public String getWorkBranchUserName() {
		return workBranchUserName;
	}
	public void setWorkBranchUserName(String workBranchUserName) {
		this.workBranchUserName = workBranchUserName;
	}
	public String getWorkManagerUserId() {
		return workManagerUserId;
	}
	public void setWorkManagerUserId(String workManagerUserId) {
		this.workManagerUserId = workManagerUserId;
	}
	public String getWorkManagerUserName() {
		return workManagerUserName;
	}
	public void setWorkManagerUserName(String workManagerUserName) {
		this.workManagerUserName = workManagerUserName;
	}
	public Construction getConstruction() {
		return construction;
	}
	public void setConstruction(Construction construction) {
		this.construction = construction;
	}
	
}
