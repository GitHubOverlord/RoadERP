package com.lida.road.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * Module entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Module implements Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private String id;
	private Module parent;
	private String name;
	private String url;
	private String functions;
	private Integer priority;
	private Integer display;
	private Integer level;
	private String subsystem;
	private String remark;
    private String image;

    // Constructors

	/** default constructor */
	public Module() {
	}

	/** full constructor */
	public Module(Module parent, String name, String url, String functions,
			Integer priority, Integer display, Integer level, String subsystem, String remark, String image) {
		this.parent = parent;
		this.name = name;
		this.url = url;
		this.functions = functions;
		this.priority = priority;
		this.display = display;
		this.level = level;
		this.subsystem = subsystem;
		this.remark = remark;
        this.image = image;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Module getParent() {
		return this.parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunctions() {
		return this.functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@SuppressWarnings("rawtypes")
	public List getSubsystems() {
	    List<String> list = new LinkedList<String>();
	    if(subsystem != null) {
		String[] subsystems = subsystem.split(",");
		if(subsystems != null) {
		    for(String s : subsystems) {
			list.add(s);
		    }
		}
	    }
	    return list;
	}

	public void setSubsystems(List<String> subsystems) {
	    subsystem = "";
	    if(subsystems != null) {
		for(String s : subsystems) {
		    subsystem = subsystem + s + ",";
		}
	    }
	    if(!subsystem.equals("")) {
		subsystem = subsystem.substring(0, subsystem.length() - 1);
	    }
	}

	public String getSubsystem() {
	    return subsystem;
	}

	public void setSubsystem(String subsystem) {
	    this.subsystem = subsystem;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
