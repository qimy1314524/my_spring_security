package com.langsin.my_spring_security.controller;

import com.langsin.my_spring_security.pojo.User;
import com.langsin.my_spring_security.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/14- 15:11
 * @desc
 **/
@RestController
public class CommonsController {

  @Autowired
  private CsrfTokenRepository csrfTokenRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @GetMapping("/gologin")
  public String goLogin() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    System.out.println(userDetails.getUsername());
    System.out.println(userDetails.getPassword());
    return "gologin";
  }


  @GetMapping("/sign")
  public User goLogin(@RequestParam("username") String username,@RequestParam("password") String password) {
    User user = new User();
    user.setUsername(username);
//    user.setPassword(EncodePassword.encodePassword(password, 5));
    user.setPassword(bCryptPasswordEncoder.encode(password));
    System.out.println("/sign==>"+user);
    return userService.insert(user);
  }

  @GetMapping("/user")
  public User goUser(@RequestParam("username") String username) {
//    UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    System.out.println(userDetails.getUsername());
    User user = userService.getUser(username);
    System.out.println("--------------------"+user);
    return user;
  }

  @GetMapping("/getCsrfToken")
//  @Cacheable(cacheNames = "tokens",key ="#root.methodName",cacheManager = "ttlCacheManager")
  public String test(HttpServletRequest request){
    CsrfToken csrf = csrfTokenRepository.generateToken(request);
    System.out.println(csrf.getHeaderName());
    System.out.println(csrf.getParameterName());
    System.out.println(csrf.getToken());
    return csrf.getToken();
  }

  @PostMapping("testPost")
  @PreAuthorize(value ="hasRole('admin') and hasAuthority('user:save')")
  public String testPost(String username,String password){
    return username+"-->"+password;
  }

  @DeleteMapping("del")
  public boolean del(String username){
    return userService.delUser(username);
  }

}
