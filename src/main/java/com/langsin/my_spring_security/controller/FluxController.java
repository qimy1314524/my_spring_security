package com.langsin.my_spring_security.controller;

import com.langsin.my_spring_security.pojo.User;
import com.langsin.my_spring_security.service.FluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author tey
 * @version V1.0
 * @date 2020/9/3- 22:09
 * @desc
 **/
@RestController
public class FluxController {

  @Autowired
  private FluxService fluxService;

  @GetMapping("/fluxUser")
  public Object getUser(Long id){
    Mono<User> userById = fluxService.getUserById(id);
    return userById;
  }

}
