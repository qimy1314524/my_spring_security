package com.langsin.my_spring_security.controller;

import com.langsin.my_spring_security.service.TestTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/21- 18:05
 * @desc
 **/
@RestController
public class TestTransactionController {

  @Autowired
  TestTransactionService testTransactionService;

  @GetMapping("test/transaction")
  public void setTestTransactionService(){
    testTransactionService.testTransaction();
  }


}
