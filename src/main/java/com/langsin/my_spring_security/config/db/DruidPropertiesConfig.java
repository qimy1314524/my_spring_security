package com.langsin.my_spring_security.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/11- 15:48
 * @desc
 **/
//@Component
//@Configuration
//@ConfigurationProperties("spring.shardingsphere.datasource.druid01")
public class DruidPropertiesConfig  extends DruidDataSource{

}
