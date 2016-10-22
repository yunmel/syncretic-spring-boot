/*
 * Copyright (c) 2016 yunmle.com(四川云麦尔科技).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.yunmel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description TODO
 *
 * @author xuyq - chainisit@126.com
 * @since 1.0 - 2016年10月22日
 */
@Controller
public class IndexController {

  @RequestMapping
  public String index() {

    return "index";
  }

}
