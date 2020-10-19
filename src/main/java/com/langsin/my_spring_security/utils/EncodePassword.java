package com.langsin.my_spring_security.utils;

import org.springframework.util.DigestUtils;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/16- 16:13
 * @desc
 **/
public class EncodePassword {

  public static String encodePassword(String password,int n){
    for (int i = 0; i <n; i++) {
      password =DigestUtils.md5DigestAsHex(password.getBytes());
    }
    return password;
  }

}
