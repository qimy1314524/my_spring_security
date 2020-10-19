package com.langsin.my_spring_security.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.langsin.my_spring_security.pojo.User02;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/16- 9:39
 * @desc
 **/
@Repository("userMapper02")

public interface User02Mapper extends BaseMapper<User02> {

  Integer batshInsert(Collection<User02> coll);


  Integer addUser(@Param("user") User02 user);

  List<User02> selectAllPage(@Param("page") Integer page,@Param("size") Integer size);

}
