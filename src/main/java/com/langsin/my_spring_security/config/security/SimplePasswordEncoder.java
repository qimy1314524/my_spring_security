package com.langsin.my_spring_security.config.security;

import com.langsin.my_spring_security.utils.EncodePassword;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/16- 14:39
 * @desc
 **/
@Component
public class SimplePasswordEncoder implements PasswordEncoder{


  @Override
  public String encode(CharSequence rawPassword) {
    return EncodePassword.encodePassword(rawPassword.toString(),7);
  }

  /**
   *使用从数据库里查询出的密码经过builder.passwordEncoder(this::passwordEncoding);
   * 加密后与this.encode(rawPassword);尽心比较 如果符合就放行登录成功matche里面encodedPassword
   * @param rawPassword 输入的密码
   * @param encodedPassword 查询的密码
   * @return
   */
  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    System.out.println("rawPassword:"+rawPassword);
    System.out.println("encodedPassword:"+encodedPassword);
    String encode = this.encode(rawPassword);
    System.out.println("encode:"+encode);
    return encodedPassword.equals(encode);
  }
}
