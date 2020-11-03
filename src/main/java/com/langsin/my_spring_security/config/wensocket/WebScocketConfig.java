package com.langsin.my_spring_security.config.wensocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/30- 17:30
 * @desc
 **/
@Configuration
public class WebScocketConfig {


  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }

}
