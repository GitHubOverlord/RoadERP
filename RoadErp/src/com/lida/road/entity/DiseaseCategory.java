package com.lida.road.entity;

import java.io.Serializable;

/***
 * 病害部位
 * @author linniantai
 *
 */
public class DiseaseCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String sn;
	private String name;
	private String standardSn;
	
	public DiseaseCategory() {
		
	}

	public DiseaseCategory(String id, String sn, String name, String standardSn){
		this.id = id;
		this.sn = sn;
		this.name = name;
		this.standardSn = standardSn;
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
}
