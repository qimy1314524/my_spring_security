package com.langsin.my_spring_security.controller;

import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * @author tey
 * @version V1.0
 * @date 2020/9/7- 17:02
 * @desc
 **/
@RestController
@Slf4j
public class CallableController {

  @GetMapping("callable01")
  public  Flux<String> callableController() throws Exception {
    log.info("主线程开始"+Thread.currentThread().getName());
    Callable<String>callable=()->{
      log.info("开始执行callable");
      Thread.sleep(10000);
      log.info("执行完毕");
      return "SUCCESS";
    };
    String call = callable.call();
    log.info("主线程执行完成"+Thread.currentThread().getName());
    return Flux.just(call);
  }

  @GetMapping("callable02")
  public  Flux<String> callableController02() throws Exception {
    log.info("主线程开始"+Thread.currentThread().getName());
    Callable<String>callable=()->{
      log.info("开始执行callable");
      Thread.sleep(10000);
      log.info("执行完毕");
      return "SUCCESS";
    };
    String call = callable.call();
    log.info("主线程执行完成"+Thread.currentThread().getName());
    return Flux.just("Successa");
  }

  @GetMapping("abcd")
  public String abc(){
    System.out.println("abc开始执行"+Thread.currentThread().getName());
    try {
      Thread.sleep(30000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("abc结束执行"+Thread.currentThread().getName());
    return "abc";
  }


}
