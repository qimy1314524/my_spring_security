/*
package com.langsin.my_spring_security.controller;

import com.langsin.my_spring_security.pojo.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @author tey
 * @version V1.0
 * @date 2020/7/29- 9:41
 * @desc
 **//*

@RestController
public class RabbitController {

  @Autowired
  private RabbitTemplate rabbitTemplate;


  @GetMapping("atguigu")
  public String atguigu(){
    User user=new User();
    user.setUsername("tey");
    user.setPassword("田恩岳");
    user.setUserId(12L);
    rabbitTemplate.convertAndSend("exchange.direct","atguigu",user);
    return "atguigu";
  }

  @GetMapping("atguiguNews")
  public String  atguiguNews(){
    rabbitTemplate.convertAndSend("exchange.direct","atguigu.news","hello atguiguNews");
    return "atguiguNews";
  }
  @GetMapping("atguiguEmps")
  public String atguiguEmps(){
    rabbitTemplate.convertAndSend("exchange.direct","atguigu.emps","hello atguiguemps");
    return "atguiguEmps";
  }
  @GetMapping("gulixueyuanNews")
  public String gulixueyuanNews(){
    rabbitTemplate.convertAndSend("exchange.direct","gulixueyuan.news","hello gulixueyuan.news");
    return "gulixueyuanNews";
  }



  @GetMapping("exchange.fanout")
  public String fanout(){
    User user=new User();
    user.setUsername("tey");
    user.setPassword("田恩岳");
    user.setUserId(12L);
    rabbitTemplate.convertAndSend("exchange.fanout","gulixueyuan.news",user);
    return "exchange.fanout";
  }





}
*/
