/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.biz.demo.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunmel.biz.demo.model.City;
import com.yunmel.biz.demo.service.CityService;

@RequestMapping("city")
@Controller
public class CityController {

  @Resource
  private CityService cityService;

  @RequestMapping
  public String index(Model model) {
    model.addAttribute("time", new Date());
    model.addAttribute("message", "Hello Spring Boot Beetl!");
    model.addAttribute("sessions", "Hello sessions!");
    model.addAttribute("pazo", "Hello sessions!");
    model.addAttribute("cities", cityService.findList());
    return "demo";
  }

  @RequestMapping("/add")
  @ResponseBody
  public City welcome() {
    City city = new City();
    city.setName("BeiJing");
    city.setState("BJ");
    cityService.save(city);
    return city;
  }

}
