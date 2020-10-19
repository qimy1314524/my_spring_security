package com.langsin.my_spring_security.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/14- 14:45
 * @desc
 **/
@Data
public class Statue  {

  @TableId
  private Integer sId;
  private String statue;
  private String  value;




}
