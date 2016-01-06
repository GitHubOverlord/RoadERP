package com.lida.road.entity;

import java.util.List;

public class BasePagerEntity<T> {
	private Pager pager;
	private List<? extends T> value;
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public List<? extends T> getValue() {
		return value;
	}
	public void setValue(List<? extends T> value) {
		this.value = value;
	}
	
	
}
