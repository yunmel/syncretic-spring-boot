/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @description 应用程序启动类
 *
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年10月22日
 */
@SpringBootApplication
public class Application {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

  @Bean(initMethod = "init", name = "beetlConfig")
  public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
    BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
    ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
    beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
    return beetlGroupUtilConfiguration;
  }

  @Bean(name = "beetlViewResolver")
  public BeetlSpringViewResolver getBeetlSpringViewResolver(
      @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
    BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
    beetlSpringViewResolver.setPrefix("/templates/");
    beetlSpringViewResolver.setSuffix(".html");
    beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
    beetlSpringViewResolver.setOrder(0);
    beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
    return beetlSpringViewResolver;
  }

}
