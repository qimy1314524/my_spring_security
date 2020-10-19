package com.langsin.my_spring_security.controller;

import com.langsin.my_spring_security.service.AsyncService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/30- 15:34
 * @desc
 **/
@RestController
public class AsyncController {

  @Autowired
  private AsyncService asyncService;

  @GetMapping("/async")
  public String async(){
    return asyncService.hello()+"dshdka";
  }

}
