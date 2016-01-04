package com.lida.road.entity;

import java.io.Serializable;

/***
 * 病害类型
 * @author linniantai
 *
 */
public class DiseaseType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String sn;
	private String name;
	private String standardSn;
	private String unit;
	private String defaultquantity;
	private String grade;
	private String judgestandard;
	private String description;
	private String note;
	private String diseaseCategoryId;
	/**关联的维护措施字段*/
	//private String maintenanceTypeName;
	
	public DiseaseType() {
		
	}

	public DiseaseType(String id, String sn, String name, String standardSn, String unit,
			String defaultquantity, String grade, String judgestandard, String description, 
			String note, String diseaseCategoryId){
		this.id = id;
		this.sn = sn;
		this.name = name;
		this.standardSn = standardSn;
		this.unit = unit;
		this.defaultquantity = defaultquantity;
		this.grade = grade;
		this.judgestandard = judgestandard;
		this.description = description;
		this.note = note;
		this.diseaseCategoryId = diseaseCategoryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandardSn() {
		return standardSn;
	}

	public void setStandardSn(String standardSn) {
		this.standardSn = standardSn;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDefaultquantity() {
		return defaultquantity;
	}

	public void setDefaultquantity(String defaultquantity) {
		this.defaultquantity = defaultquantity;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getJudgestandard() {
		return judgestandard;
	}

	public void setJudgestandard(String judgestandard) {
		this.judgestandard = judgestandard;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDiseaseCategoryId() {
		return diseaseCategoryId;
	}

	public void setDiseaseCategoryId(String diseaseCategoryId) {
		this.diseaseCategoryId = diseaseCategoryId;
	}

//	public String getMaintenanceTypeName() {
//		if (StrTool.isNotBlank(maintenanceTypeId)){
//			MaintenanceTypeService srv = SpringInit.getApplicationContext().getBean(MaintenanceTypeService.class);
//			MaintenanceType type = srv.getMaintenanceTypeBySn(maintenanceTypeId);
//			if (type!=null){
//				return type.getName();
//			}
//		}
//		return maintenanceTypeName;
//	}

//	public void setMaintenanceTypeName(String maintenanceTypeName) {
//		this.maintenanceTypeName = maintenanceTypeName;
//	}
}
