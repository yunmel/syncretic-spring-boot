/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel.core.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * 
 * @description 特别注意，该接口不能被扫描到，否则会出错
 *
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年10月22日
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
