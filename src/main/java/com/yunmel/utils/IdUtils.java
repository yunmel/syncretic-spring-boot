/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.utils;

/**
 * 
 * @description ID生成工具类
 *
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年10月22日
 */
public class IdUtils {
  private static final IdWorker idWorker = new IdWorker(5, 1);
  public static final Long getId() {
    return idWorker.nextId();
  }
  
}
