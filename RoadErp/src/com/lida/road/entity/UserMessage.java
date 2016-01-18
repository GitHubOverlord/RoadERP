package com.lida.road.entity;

import java.util.List;

public class UserMessage {
	private Admin admin;
	private List<DiseaseCategory> diseaseCategoryList;
	private List<DiseaseType> diseaseTypeList;
	
	public UserMessage(){
		
	}
	public UserMessage(Admin admin, List<DiseaseCategory> diseaseCategories,
			List<DiseaseType> diseaseTypes) {
		super();
		this.admin = admin;
		this.diseaseCategoryList = diseaseCategories;
		this.diseaseTypeList = diseaseTypes;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public List<DiseaseCategory> getDiseaseCategoryList() {
		return diseaseCategoryList;
	}
	public void setDiseaseCategoryList(List<DiseaseCategory> diseaseCategoryList) {
		this.diseaseCategoryList = diseaseCategoryList;
	}
	public List<DiseaseType> getDiseaseTypeList() {
		return diseaseTypeList;
	}
	public void setDiseaseTypeList(List<DiseaseType> diseaseTypeList) {
		this.diseaseTypeList = diseaseTypeList;
	}
	
	
}
