package com.lida.road.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Module entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("rawtypes")
public class Role  implements java.io.Serializable {


    // Fields    

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String remark;
    private String subSystem;
    private Set<Module> modules = new HashSet<Module>(0);


    // Constructors

    /** default constructor */
    public Role() {
    }

    
    /** full constructor */
    public Role(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}


	public String getSubSystem() {
		return subSystem;
	}


	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}
	
}
