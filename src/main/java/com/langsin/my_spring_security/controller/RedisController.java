package com.langsin.my_spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tey
 * @version V1.0
 * @date 2020/9/30- 10:16
 * @desc
 **/
@RestController
public class RedisController {

  @Autowired
  private StringRedisTemplate redisTemplate;
  @GetMapping("/setkey")
  public void setkey(String key,String value){
    redisTemplate.opsForValue().set(key,value);
    System.out.println("redis存入数据: "+key+" :"+value);
  }

  @GetMapping("/getkey")
  public String getkey(String key){
    String v = redisTemplate.opsForValue().get(key);
    System.out.println("redis取出数据: "+v);
    return v;
  }

}
