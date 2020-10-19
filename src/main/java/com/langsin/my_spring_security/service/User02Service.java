package com.langsin.my_spring_security.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.langsin.my_spring_security.dao.mapper.User02Mapper;
import com.langsin.my_spring_security.pojo.User02;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/13- 16:37
 * @desc
 **/
@Service
@DS("default")
public class User02Service {

  @Autowired
  private User02Mapper user02Mapper;

  public Integer batshInsert(Collection<User02> coll){
    return user02Mapper.batshInsert(coll);
  }


 public Integer addUser(@Param("user") User02 user){
   return user02Mapper.addUser(user);
 }

 public List<User02> selectAllPage(@Param("page") Integer page,@Param("size") Integer size){
   return user02Mapper.selectAllPage(page,size);
 }



  public List<User02> startPage(int pageNum,int pageSize){
    PageHelper.startPage(pageNum,pageSize);
    List<User02> user02s = user02Mapper.selectList(null);
    return user02s;
  }
 }
