package com.langsin.my_spring_security.config.security;

import com.alibaba.druid.pool.DruidDataSource;
import com.langsin.my_spring_security.config.db.MyJdbcTokenRepositiry;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/14- 14:55
 * @desc
 **/

/**
 * 前端的表单要加一个 <input type="hidden" name="${_csrf.parameterName}" value=${_csrf.token}/>
 * _csrf是为了防止跨站请求伪造 SpringSecurity默认账号密码请求参数名称是username password 可以使用usernameParameter
 * passwordParameter进行修改 记住密码默认事remember-me
 */
@Configuration
/**
 *启用web环境下的权限控制功能
 */
@EnableWebSecurity
/**
 * 启用全局方法权限控制功能,并设置prePostEnabled=true,保证@PreAuthority,@PostAuthority
 * @PreFilter,@PostFilter 起作用
 * @PreAuthorize(value ="hasRole('admin') and hasAuthority('user:save')")
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSerurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private DruidDataSource masterspringsecurity;
  @Autowired
  private SimpleUserDetailServiceImpl simpleUserDetailService;

  @Override
  protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//    内存
    /*builder
        .inMemoryAuthentication()
        .passwordEncoder(new BCryptPasswordEncoder())
        .withUser("20167307")
        .password(new BCryptPasswordEncoder().encode("619619tey"))
        .roles("ADMIN");*/

    builder.userDetailsService(simpleUserDetailService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity security) throws Exception {
    MyJdbcTokenRepositiry tokenRepository = new MyJdbcTokenRepositiry();
    tokenRepository.setDataSource(masterspringsecurity);
//    tokenRepository.setCreateTableOnStartup(true);
//    tokenRepository.initDao();

    security
        //对请求进行授权
        .authorizeRequests()
        //针对common/login进行授权
        .antMatchers("/login","/getCsrfToken")
//        .antMatchers("/**")
        //可以无条件访问
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/actuator/**","/druid/**")
        .hasRole("ADMIN")
        .and()
        //对请求进行授权
        .authorizeRequests()
        //任意请求
        .anyRequest()
//        .permitAll()
        //需要登录以后才能访问
        .authenticated()
        .and()
        .formLogin()
        .defaultSuccessUrl("/user?username=20167307")
        .and()
        .logout().logoutSuccessUrl("/login")
        /**
         * 针对druid无法登陆的问题,可以.and().csrf().disable()禁用csrf或者左如下配置
         */
//        .and().csrf().disable()
         .and().csrf().ignoringAntMatchers("/druid/*")
        //登录页面
//        .loginPage("/")
        //登录表单处理,登录地址本身也需要放行
//        .loginProcessingUrl("")
//    .permitAll()

//    .and().csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository())
//    .and().exceptionHandling()
//    .accessDeniedPage("");
//        .and().rememberMe().tokenRepository(tokenRepository)
    ;
  }


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CsrfTokenRepository csrfTokenRepository() {
    return new HttpSessionCsrfTokenRepository();
  }


}
