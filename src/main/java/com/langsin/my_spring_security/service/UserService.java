package com.langsin.my_spring_security.service;

import com.langsin.my_spring_security.dao.mapper.User02Mapper;
import com.langsin.my_spring_security.dao.mapper.UserMapper;
import com.langsin.my_spring_security.pojo.User;
import com.langsin.my_spring_security.pojo.User02;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/22- 10:55
 * @desc
 **/
@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private User02Mapper user02Mapper;

  /**
   *
   * @param username
   * @return User
   * @des
   * @Cacheable不能配置 condition = "#result != null" ，
   * 因为这个注解在进入方法前去检测condition，
   * 而这时还没有result，会造成result为null的情况。
   */
  @Transactional(rollbackFor = {Error.class, Exception.class})
  @Cacheable(cacheNames = "user", key = "#username",unless="#result == null")
  public User getUser(String username) {
    User user = userMapper.getUser(username);
    return user;
  }

  @Transactional(rollbackFor = {Error.class, Exception.class},noRollbackFor = {ArithmeticException.class})
  @CachePut(cacheNames = "user", key = "#user.username", condition = "#result!=null")
  public User insert(User user) {
    Integer insert = userMapper.insert(user);
    System.out.println("=================="+insert);
    System.out.println(1/0);
    if (insert == 1) {
      return user;
    }
    return null;
  }

  @Transactional(rollbackFor = {Error.class, Exception.class})
  @CacheEvict(cacheNames = "user", key = "#username",condition = "#result==true")
  public boolean delUser(String username) {
    Integer integer = userMapper.delByUsername(username);
    return integer==1;
  }

  public List<User02> selectAllPage(Integer page,Integer size){
    return user02Mapper.selectAllPage((page-1)*size,size);
  }




}
