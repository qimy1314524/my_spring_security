package com.langsin.my_spring_security.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.langsin.my_spring_security.pojo.User;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/16- 9:39
 * @desc
 **/
@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {

  /**
   * 通过用户名查询user
   */
  User getUser(String username);

  User getUser(Long username);


  /**
   * 通过用户名删除
   */
  Integer delByUsername(String username);

  Map<String,String> explain(String name);

}
