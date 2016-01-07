package com.lida.road.entity;

import java.util.List;

public class BasePagerEntity {
	private Pager pager;
	private List<?> list;
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
	
	
}
