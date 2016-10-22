/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.biz.demo.model;

import com.yunmel.core.base.BaseEntity;

public class City extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private String state;
	private Integer delFlag;
	public String getName() {
		return this.getString("name");
	}
	public void setName(String name) {
		this.put("name", name);
	}
	public String getState() {
		return this.getString("state");
	}
	public void setState(String state) {
		this.put("state", state);
	}
	public Integer getDelFlag() {
		return this.getInteger("delFlag");
	}
	public void setDelFlag(Integer delFlag) {
		this.put("delFlag", delFlag);
	}
	
}
