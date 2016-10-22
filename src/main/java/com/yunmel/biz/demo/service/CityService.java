/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.biz.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yunmel.biz.demo.mapper.CityMapper;
import com.yunmel.biz.demo.model.City;
import com.yunmel.core.base.BaseService;

@Service
public class CityService extends BaseService<City> {

	@Autowired
	private CityMapper cityMapper;

	public List<City> getAll(City city) {
		if (city.getPage() != null && city.getRows() != null) {
			PageHelper.startPage(city.getPage(), city.getRows(), "id");
		}
		return cityMapper.selectAll();
	}

	public City getById(Integer id) {
		return cityMapper.selectByPrimaryKey(id);
	}

	public void deleteById(Integer id) {
		cityMapper.deleteByPrimaryKey(id);
	}

	public void save(City country) {
		if (country.getId() != null) {
			cityMapper.updateByPrimaryKey(country);
		} else {
			super.insertSelective(country);
		}
	}

	public List<City> findList() {
		return cityMapper.findList();
	}
}
