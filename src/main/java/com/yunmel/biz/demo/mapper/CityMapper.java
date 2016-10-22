/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.biz.demo.mapper;


import java.util.List;

import com.yunmel.biz.demo.model.City;
import com.yunmel.core.base.BaseMapper;

public interface CityMapper extends BaseMapper<City> {
	
	public List<City> findList();
}
