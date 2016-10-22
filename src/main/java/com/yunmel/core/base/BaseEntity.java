/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.core.base;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;

@Entity
public class BaseEntity extends JSONObject {
	private static final long serialVersionUID = -6259412242789016371L;
	@Id
	private Long id;

	@Transient
	private Integer page = 1;

	@Transient
	private Integer rows = 10;

	public Long getId() {
		return this.getLong("id");
	}

	public void setId(Long id) {
		this.put("id", id);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
