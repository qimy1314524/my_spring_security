package com.langsin.my_spring_security.config.db;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/15- 16:18
 * @desc
 **/
public class MyJdbcTokenRepositiry extends JdbcTokenRepositoryImpl {

  private boolean createTableOnStartup;

  @Override
  protected void initDao() {
    if (this.createTableOnStartup) {
      this.getJdbcTemplate().execute("create table if not exists persistent_logins (username varchar(64) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)");
    }
  }
}
