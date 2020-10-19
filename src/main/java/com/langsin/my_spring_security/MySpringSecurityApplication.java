package com.langsin.my_spring_security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.langsin.my_spring_security.dao.mapper")//mybaits包扫描
@EnableCaching//开启缓存注解
@EnableRabbit//开启rabbitmq功能
@EnableAsync//开启异步注解任务功能
@EnableScheduling//开启定时任务注解功能
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class MySpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringSecurityApplication.class, args);
	}

}
