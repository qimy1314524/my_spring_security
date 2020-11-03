package com.langsin.my_spring_security.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/30- 16:43
 * @desc
 **/
@Service
public class ScheduleService {

  /**
   * on the second, minute, hour, day of month, month, and day of week.
   * {@code "0 * * * * MON-FRI"}
   */
//  @Scheduled(cron = "0 * * * * MON-FRI")
  public void scheduleService(){
    System.out.println("hello"+ LocalDateTime.now());
  }
}
