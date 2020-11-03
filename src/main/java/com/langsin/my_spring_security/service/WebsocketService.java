package com.langsin.my_spring_security.service;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/30- 17:35
 * @desc
 **/
@Slf4j
@Service
@ServerEndpoint(value = "/websocket/{userInfo}")
public class WebsocketService {

  @OnOpen
  public void onOepn(Session session){
    System.out.println("open");
    System.out.println(session.getPathParameters());
  }

  @OnMessage
  public void onMessage(String message){

  }

  @OnClose
  public void onClose(){
    System.out.println("close");

  }

  @OnError
  public void onError(Throwable throwable){
    throwable.printStackTrace();
  }




}
