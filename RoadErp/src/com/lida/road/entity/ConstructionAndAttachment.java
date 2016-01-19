package com.lida.road.entity;

import java.io.Serializable;
import java.util.List;

public class ConstructionAndAttachment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2822186138259586472L;
	private Construction construction;
	private List<AffixFile> affixConstructionList;
	private List<AffixFile> affixDiseaseRecordList;
	private List<AffixFile> affixSupervisorList;

	public Construction getConstruction() {
		return construction;
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
	}

	public List<AffixFile> getAffixConstructionList() {
		return affixConstructionList;
	}

	public void setAffixConstructionList(List<AffixFile> affixConstructionList) {
		this.affixConstructionList = affixConstructionList;
	}

	public List<AffixFile> getAffixDiseaseRecordList() {
		return affixDiseaseRecordList;
	}

	public void setAffixDiseaseRecordList(List<AffixFile> affixDiseaseRecordList) {
		this.affixDiseaseRecordList = affixDiseaseRecordList;
	}

	public List<AffixFile> getAffixSupervisorList() {
		return affixSupervisorList;
	}

	public void setAffixSupervisorList(List<AffixFile> affixSupervisorList) {
		this.affixSupervisorList = affixSupervisorList;
	}

}
