package com.langsin.my_spring_security.pojo;

import static com.baomidou.mybatisplus.enums.IdType.AUTO;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/11- 13:23
 * @desc
 **/
@Data
public class Course implements Comparable<Long>{

  @TableId
  private Long cid;
  private String  cname;
  private Long userId;
  private String cstatue;

  @Override
  public int compareTo(Long o) {
    return 0;
  }
}
