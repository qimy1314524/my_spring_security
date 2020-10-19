package com.langsin.my_spring_security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tey
 * @version V1.0
 * @date 2020/8/31- 10:27
 * @desc 语法结构 execution([权限修饰符][返回类型][类全路径][方法名称][参数列表]) 举例: execution(*com.langsin.my_spring_security.controller.CommonsController.goUser(..))
 * execution(* com.langsin.my_spring_security.controller.CommonsController.*(..)) execution(*
 * com.langsin.my_spring_security.controller.*.*(..))
 * 如果多个增强类对同一个方法进行增强,设置增强类优先级
 * 在类上使用@Order(数字类型值) 值越小优先级越高
 **/

@Component
@Aspect
public class AspectTest {

  @Pointcut(value = "execution(* com.langsin.my_spring_security.controller.ThymeLeafHtml.*(..))")
  public void log() {
  }


  /**
   * 作为前置通知
   */
  @Before(value = "log()")
  public void before() {
    System.out.println("aspectj执行 ---------------------------前置通知");
  }

  @After(value = "log()")
  public void after() {
    System.out.println("aspectj执行 ---------------------------后置通知");
  }

  @AfterReturning(value = "log()",returning = "obj")
  public Object afterReturning(Object obj) {
    System.out.println(obj);
    System.out.println("aspectj执行 ---------------------------AfterReturning通知");
    return obj;
  }

  @AfterThrowing(value = "log()",throwing = "throwable")
  public void afterThrowing(Throwable throwable) {
    System.out.println(throwable);
    System.out.println("aspectj执行 ---------------------------AfterThrowing通知");
  }


  @Around(value = "log()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("aspectj执行 ---------------------------around通知之前---");
    Object proceed = joinPoint.proceed();
    System.out.println("aspectj执行 ---------------------------around通知之后---");
    return proceed;
  }

}
