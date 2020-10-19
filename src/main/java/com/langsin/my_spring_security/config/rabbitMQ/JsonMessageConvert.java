package com.langsin.my_spring_security.config.rabbitMQ;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/24- 13:52
 * @desc
 * 自动配置类RabbitAutoConfiguration
 * 又自动配置了连接工厂ConnectionFactory
 * RabbitProperties封装了RabbitMQ的配置
 * RabbitTemplate给RabbitMQ发送以及接收消息
 * AmqpAdmin:RabbitMQ系统管理功能组件
 * AdqpAdmin 创建和删除Queue,Exchange,Binding
 * 使用@EnableRabbit 开启rabbit自动配置功能
 * 使用@RabbitListener(queues = {"atguigu","atguigu.news","atguigu.emps","gulixueyuan.news"}) 监听消息队列
 *
 *
 **/
@Configuration
public class JsonMessageConvert {

  /**
   * 配置消息转换器
   * @return
   */
  @Bean
  public MessageConverter messageConverter(){
    return new Jackson2JsonMessageConverter();
  }

}
