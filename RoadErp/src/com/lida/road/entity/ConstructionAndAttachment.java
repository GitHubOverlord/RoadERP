package com.lida.road.entity;

import java.util.List;

public class ConstructionAndAttachment {
	private Construction construction;
	private List<String> affixConstructionList;
	private List<String> affixDiseaseRecordList;
	private List<String> affixSupervisorList;
	public Construction getConstruction() {
		return construction;
	}
	public void setConstruction(Construction construction) {
		this.construction = construction;
	}
	public List<String> getAffixConstructionList() {
		return affixConstructionList;
	}
	public void setAffixConstructionList(List<String> affixConstructionList) {
		this.affixConstructionList = affixConstructionList;
	}
	public List<String> getAffixDiseaseRecordList() {
		return affixDiseaseRecordList;
	}
	public void setAffixDiseaseRecordList(List<String> affixDiseaseRecordList) {
		this.affixDiseaseRecordList = affixDiseaseRecordList;
	}
	public List<String> getAffixSupervisorList() {
		return affixSupervisorList;
	}
	public void setAffixSupervisorList(List<String> affixSupervisorList) {
		this.affixSupervisorList = affixSupervisorList;
	}
	
}
