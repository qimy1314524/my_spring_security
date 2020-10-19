package com.langsin.my_spring_security.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.langsin.my_spring_security.pojo.Course;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/11- 13:27
 * @desc
 **/
@Repository
public interface CourseMapper extends BaseMapper<Course> {

  Integer batshInsert(Collection<Course> coll);

  Integer addCourse(@Param("course") Course course);

  List<Course> selectAllPage(@Param("page") Integer page,@Param("size") Integer size);


}
