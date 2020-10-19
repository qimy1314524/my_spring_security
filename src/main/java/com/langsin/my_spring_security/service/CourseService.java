package com.langsin.my_spring_security.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.langsin.my_spring_security.dao.mapper.CourseMapper;
import com.langsin.my_spring_security.pojo.Course;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/13- 16:33
 * @desc
 **/
@Service
public class CourseService  {

  @Autowired
  private CourseMapper courseMapper;

  public List<Course> startPage(){
    PageHelper.startPage(2, 5);
    List<Course> courses = courseMapper.selectList(null);
    return courses;
  }

}
