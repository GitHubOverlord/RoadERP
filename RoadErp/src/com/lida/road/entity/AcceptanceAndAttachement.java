package com.lida.road.entity;

import java.util.List;

public class AcceptanceAndAttachement {
	private List<AffixFile> affixConstructionList;
	private List<AffixFile> affixSupervisorList;
	private List<AffixFile> affixDiseaseRecordList;
	private Acceptance acceptance;

	public List<AffixFile> getAffixConstructionList() {
		return affixConstructionList;
	}

	public void setAffixConstructionList(List<AffixFile> affixConstructionList) {
		this.affixConstructionList = affixConstructionList;
	}

	public Acceptance getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(Acceptance acceptance) {
		this.acceptance = acceptance;
	}

	public List<AffixFile> getAffixSupervisorList() {
		return affixSupervisorList;
	}

	public void setAffixSupervisorList(List<AffixFile> affixSupervisorList) {
		this.affixSupervisorList = affixSupervisorList;
	}

	public List<AffixFile> getAffixDiseaseRecordList() {
		return affixDiseaseRecordList;
	}

	public void setAffixDiseaseRecordList(List<AffixFile> affixDiseaseRecordList) {
		this.affixDiseaseRecordList = affixDiseaseRecordList;
	}

}
