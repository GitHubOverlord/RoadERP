package com.lida.road.entity;

import java.io.Serializable;

public class LocationModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String longitude;
	private String latitude;
	private String sendDate;
	private String sendTime;
	private String userId;
	private String startAllTime;
	private String endAllTime;
	private String sortNo;
	private String routeSn;
	private String stack;
	private String checkType;
	private String content;
	private String beginTime;
	private String finshTime;

	public LocationModel() {

	}

	public LocationModel(String id, String longitude, String latitude,
			String sendDate, String sendTime, String userId,
			String startAllTime, String endAllTime, String sortNo,
			String routeSn, String stack, String checkType, String content,
			String beginTime, String finshTime) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.userId = userId;
		this.startAllTime = startAllTime;
		this.endAllTime = endAllTime;
		this.sortNo = sortNo;
		this.routeSn = routeSn;
		this.stack = stack;
		this.checkType = checkType;
		this.content = content;
		this.beginTime = beginTime;
		this.finshTime = finshTime;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getStartAllTime() {
		return startAllTime;
	}

	public void setStartAllTime(String startAllTime) {
		this.startAllTime = startAllTime;
	}

	public String getEndAllTime() {
		return endAllTime;
	}

	public void setEndAllTime(String endAllTime) {
		this.endAllTime = endAllTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRouteSn() {
		return routeSn;
	}

	public void setRouteSn(String routeSn) {
		this.routeSn = routeSn;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getFinshTime() {
		return finshTime;
	}

	public void setFinshTime(String finshTime) {
		this.finshTime = finshTime;
	}

}
