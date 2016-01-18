package com.lida.road.entity;

import java.io.Serializable;
import java.util.List;

public class AttachmentAndDisease implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3070876617231225958L;
	private DiseaseRecord diseaseRecord;
	private List<String> affixList;
	public DiseaseRecord getDiseaseRecord() {
		return diseaseRecord;
	}
	public void setDiseaseRecord(DiseaseRecord diseaseRecord) {
		this.diseaseRecord = diseaseRecord;
	}
	public List<String> getAffixList() {
		return affixList;
	}
	public void setAffixList(List<String> affixList) {
		this.affixList = affixList;
	}
}
