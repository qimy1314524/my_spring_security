package com.langsin.my_spring_security.service;

import com.langsin.my_spring_security.dao.mapper.UserMapper;
import com.langsin.my_spring_security.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author tey
 * @version V1.0
 * @date 2020/9/3- 22:10
 * @desc
 **/
@Service
public class FluxService {

  @Autowired
  private UserMapper userMapper;

  public Mono<User> getUserById(Long id){
     User user = userMapper.getUser(id);
    System.out.println(user);
    return Mono.just(user);
  }

  public Flux<User> getUserByIdFlux(Long id){
    User user = userMapper.getUser(id);
    System.out.println(user);
    return Flux.just(user);
  }

}
