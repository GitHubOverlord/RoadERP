package com.lida.road.entity;

import java.util.List;

public class AcceptanceAndAttachement {
	List<String> affixConstructionList;
	Acceptance acceptance;
	List<String> affixSupervisorList;
	public List<String> getAffixConstructionList() {
		return affixConstructionList;
	}
	public void setAffixConstructionList(List<String> affixConstructionList) {
		this.affixConstructionList = affixConstructionList;
	}
	public Acceptance getAcceptance() {
		return acceptance;
	}
	public void setAcceptance(Acceptance acceptance) {
		this.acceptance = acceptance;
	}
	public List<String> getAffixSupervisorList() {
		return affixSupervisorList;
	}
	public void setAffixSupervisorList(List<String> affixSupervisorList) {
		this.affixSupervisorList = affixSupervisorList;
	}
	

}
