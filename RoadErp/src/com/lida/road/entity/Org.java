package com.lida.road.entity;

import java.io.Serializable;

public class Org implements Serializable {
    private static final long serialVersionUID = -4857756469643231598L;
    
    public static final String ORGTYPE_OWNER = "owner";
    public static final String ORGTYPE_CONSTRUCTPARTY = "constructParty";
    public static final String ORGTYPE_SUPERVISION = "supervision";
    
    private String id; //编号
    private String name; //管养单位名称
    private Org parent; //父编号
    private String parentId;//
    private Integer available = 0;
	private Integer level;//层
	private Integer priority;
	private String orgCode;//行政区划编号
	private String orgName;//行政区名称
	private String orgNumber;//机构编号
	private String address;//地址
	private String telephone;//电话
	private String manager;//管理人员
	private String email;//邮箱
	private String webAddress;//网址
	private String remark;//备注
	private String cityState;//属于的市区
	private String treeCode;//treeCode排序用
	
	private String orgType;//机构类型
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Org that = (Org) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

//    @Override
//    public String toString() {
//        return "Org{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", parentId=" + parent.getId() +
//                ", available=" + available +
//                '}';
//    }
    public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentId() {
		if (parent ==null){
			return "-1";
		} else {
			return parent.id;
		}
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}
	
	
}
