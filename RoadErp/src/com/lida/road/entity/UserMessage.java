package com.lida.road.entity;

import java.util.List;

public class UserMessage {
	private Admin admin;
	private List<DiseaseCategory> diseaseCategories;
	private List<DiseaseType> diseaseTypes;
	public UserMessage(){
		
	}
	public UserMessage(Admin admin, List<DiseaseCategory> diseaseCategories,
			List<DiseaseType> diseaseTypes) {
		super();
		this.admin = admin;
		this.diseaseCategories = diseaseCategories;
		this.diseaseTypes = diseaseTypes;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public List<DiseaseCategory> getDiseaseCategories() {
		return diseaseCategories;
	}
	public void setDiseaseCategories(List<DiseaseCategory> diseaseCategories) {
		this.diseaseCategories = diseaseCategories;
	}
	public List<DiseaseType> getDiseaseTypes() {
		return diseaseTypes;
	}
	public void setDiseaseTypes(List<DiseaseType> diseaseTypes) {
		this.diseaseTypes = diseaseTypes;
	}
	
}
