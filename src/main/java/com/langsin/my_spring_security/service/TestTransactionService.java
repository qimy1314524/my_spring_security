package com.langsin.my_spring_security.service;

import com.langsin.my_spring_security.dao.mapper.CourseMapper;
import com.langsin.my_spring_security.dao.mapper.User02Mapper;
import com.langsin.my_spring_security.pojo.Course;
import com.langsin.my_spring_security.pojo.User02;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/21- 18:06
 * @desc
 **/
@Service
public class TestTransactionService {

  @Autowired
  private CourseMapper courseMapper;
  @Autowired
  private User02Mapper user02Mapper;


  @ShardingTransactionType(TransactionType.LOCAL)
  @Transactional(rollbackFor = Throwable.class)
  public void testTransaction(){
    Course course=new Course();
    course.setCstatue("3");
    course.setCname("测试事务01");
    course.setUserId(100L);
    Integer insert = courseMapper.addCourse(course);
    User02 user02=new User02();
    String uuid = UUID.randomUUID().toString();
    user02.setUsername(uuid.substring(0, 5));
    user02.setPassword(uuid.substring(0, 8));
    user02.setAddress("测试事务01" + "address");
    user02.setAge(100);
    user02.setEmail(uuid.substring(0, 5) + "@qq.com");
    user02.setGender(new Random().nextInt(2));
    user02Mapper.addUser(user02);
    System.out.println(10/0);
  }

}
