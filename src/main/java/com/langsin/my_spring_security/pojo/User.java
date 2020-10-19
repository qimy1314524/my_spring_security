package com.langsin.my_spring_security.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/16- 9:33
 * @desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@Component
public class User implements Serializable{

  @TableId
  @Id
  private Long userId;
  private String username;
  private String password;


}
