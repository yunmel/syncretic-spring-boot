/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.core.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.yunmel.utils.IdUtils;

/**
 * 
 * @description service 基类
 *
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年7月15日
 * @param <T>
 */
public abstract class BaseService<T extends BaseEntity> {

	@Autowired
	private BaseMapper<T> baseMapper;

	public int insertSelective(T record) {
		record.setId(IdUtils.getId());
		return baseMapper.insertSelective(record);
	}
}