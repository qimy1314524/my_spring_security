package com.langsin.my_spring_security.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/29- 9:36
 * @desc
 **/
//@Service
public class RabblitService {

  @Autowired
  MessageConverter messageConverter;

  @RabbitListener(queues = {"atguigu", "atguigu.news", "atguigu.emps", "gulixueyuan.news"})
  public void receive(Message message) throws ClassNotFoundException, IOException {
    Object obj = messageConverter.fromMessage(message);
    System.out.println(obj);
  }


}
