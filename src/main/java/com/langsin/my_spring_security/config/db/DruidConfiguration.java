package com.langsin.my_spring_security.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import javax.sql.DataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Druid 配置
 *
 * @author linrf
 * @version V1.0
 * @date 2020/5/19 14:27
 */
@Slf4j
@Configuration
public class DruidConfiguration {





    /**
     * 配置监控服务器
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet(StatViewServletConfiguration statViewServletConfig) {
        log.info("init Druid Servlet Configuration ");
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), statViewServletConfig.getUrlPattern());
        // IP白名单
        if (null != statViewServletConfig.getAllow() && !"".equals(statViewServletConfig.getAllow())) {
            servletRegistrationBean.addInitParameter("allow", statViewServletConfig.getAllow());
        }
        // IP黑名单(共同存在时，deny优先于allow)
        if (null != statViewServletConfig.getDeny() && !"".equals(statViewServletConfig.getDeny())) {
            servletRegistrationBean.addInitParameter("deny", statViewServletConfig.getDeny());
        }
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", statViewServletConfig.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", statViewServletConfig.getLoginPassword());
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", statViewServletConfig.getResetEnable().toString());
        servletRegistrationBean.addInitParameter("sessionStatEnable", statViewServletConfig.getSessionStatEnable().toString());
        return servletRegistrationBean;
    }

    /**
     * 配置监控服务过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean(WebStatFilterConfiguration webStatFilterConfig) {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns(webStatFilterConfig.getUrlPattern());
        filterRegistrationBean.addInitParameter("exclusions", webStatFilterConfig.exclusions);
        return filterRegistrationBean;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "spring.druid.stat-view-servlet")
    public static class StatViewServletConfiguration {
        /**
         * 登录用户
         * 默认：admin
         */
        private String loginUsername = "admin";
        /**
         * 登录密码
         * 默认：admin123456
         */
        private String loginPassword = "admin123456";
        /**
         * 默认：false
         */
        private Boolean resetEnable = false;
        /**
         * 访问监控地址
         * 默认：/druid/*
         */
        private String urlPattern = "/druid/*";
        /**
         * 添加IP白名单
         * 默认：空
         */
        private String allow;
        /**
         * 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
         * 默认：空
         */
        private String deny;
        /**
         * 是否监控session
         * 默认：true
         */
        private Boolean sessionStatEnable = true;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "spring.druid.web-stat-filter")
    public static class WebStatFilterConfiguration {
        /**
         * 添加过滤规则
         * 默认：/*
         */
        private String urlPattern = "/*";
        /**
         * 忽略过滤格式
         * 默认：*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
         */
        private String exclusions ="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";
    }
}
