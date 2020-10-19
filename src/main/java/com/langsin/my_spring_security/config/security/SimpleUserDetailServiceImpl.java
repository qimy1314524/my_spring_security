package com.langsin.my_spring_security.config.security;

import com.langsin.my_spring_security.service.UserService;
import com.langsin.my_spring_security.utils.EncodePassword;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/15- 16:48
 * @desc
 **/
@Component("SimpleUserDetailServiceImpl")
public class SimpleUserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

//  @Autowired
//  private SimplePasswordEncoder passwordEncoder;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private String passwordEncoding(String password) {
    return EncodePassword.encodePassword(password, 2);
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    进行查询  封装
    com.langsin.my_spring_security.pojo.User user = userService.getUser(username);
    if (user == null) {
      throw new RuntimeException("用户不存在");
    }
    String password = user.getPassword();
    UserBuilder builder = User.builder();
    /**
     * 从数据库里获取的数据使用一下方式进行加密,
     * 加密后的password为matches的encodedPassword参数
     */
    List<String> list=new ArrayList<>();
    if("20167307".equals(username)){
      list.add("ADMIN");
    }
//    builder.passwordEncoder(this::passwordEncoding);
    builder.username(username);
    builder.password(password);
    builder.roles(list.toArray(new String[list.size()]));
    UserDetails build = builder.build();
    return build;
  }
}
