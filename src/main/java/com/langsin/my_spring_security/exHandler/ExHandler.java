package com.langsin.my_spring_security.exHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author tey
 * @version V1.0
 * @date 2020/8/4- 9:37
 * @desc
 **/
@RestControllerAdvice
public class ExHandler {

  @ExceptionHandler(value = Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, String> handlerException(Exception ex) {
    Map<String, String> errorMessage = new LinkedHashMap<>(8);
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String url = request.getRequestURI();
      String remoteAddr = request.getRemoteAddr();
      String method = request.getMethod();
      errorMessage.put("请求地址", url);
      errorMessage.put("请求来源地址", remoteAddr);
      errorMessage.put("请求方法", method);
    }
    errorMessage.put("异常内容", ex.fillInStackTrace().getLocalizedMessage());
//    return ResponseVo.enumMessage(ResponseEnum.INTERNAL_SERVER_ERROR, errorMessage);
    return errorMessage;
  }


}
