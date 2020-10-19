package com.langsin.my_spring_security.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/30- 15:36
 * @desc
 **/
@Service
public class AsyncService {


  @Async
  public String hello() {
    try {
      System.out.println("开始线程处理"+Thread.currentThread().getName());
      Thread.sleep(10000L);
      System.out.println("线程处理结束"+Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "数据处理成功";
  }




}
