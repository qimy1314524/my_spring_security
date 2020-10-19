package com.langsin.my_spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/22- 16:07
 * @desc
 **/
@Controller
public class ThymeLeafHtml {

  @GetMapping("testPost")
  public String testPost(){
    return "testPost";
  }
}
