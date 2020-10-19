package com.langsin.my_spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/30- 17:23
 * @desc
 **/
@RestController
public class MailController {
  @Autowired
  private JavaMailSenderImpl javaMailSender;

  @GetMapping("sendMail")
  public void sendMail(){
    SimpleMailMessage message=new SimpleMailMessage();
    //邮件设置
    message.setSubject("通知-今晚开会");
    message.setText("今晚6点开会,需求规格文档讨论");
    message.setTo("tianey@geovis.com.cn");
    message.setFrom("1499989399@qq.com");
    javaMailSender.send(message);
  }

}
