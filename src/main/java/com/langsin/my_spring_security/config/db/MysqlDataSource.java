/*
package com.langsin.my_spring_security.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

*/
/**
 * @author tey
 * @version V1.0
 * @date 2020/7/15- 15:40
 * @desc
 **//*

//@Configuration
public class MysqlDataSource {


  @Autowired
  private MybatisProperties properties;
  @Autowired
  private Environment environment;

  @Autowired
  private DruidDataSource druidDataSource;

//  @Bean
//  @ConfigurationProperties("spring.datasource.mysql")
//  public DruidDataSource druidDataSource() {
//    DruidDataSource dataSource = new DruidDataSource();
//    return dataSource;
//  }

  @Bean
  @ConfigurationProperties("spring.shardingsphere.datasource.druid01")
  public DruidDataSource druidDataSource() {
    DruidDataSource dataSource=new DruidPropertiesConfig();
    return new DruidDataSource();
  }



  */
/**
   * @throws PropertyVetoException
   * 事务传播行为: required 如果有事务在运行,当前的方法就在这个事务内运行,否则就启动一个新的事物,并在自己的事务内运行
   * required_new 当前的方法必须启动新的事务,并在他自己的事务内运行,如果有事务正在运行,应该将它挂起
   * supports 如果有事务在运行,当前的方法就在这个事务内运行,否则他可以不运行在事务内
   * not_support 当前的方法不应该运行在事务中,如果有事务,就将它挂起
   * mandatory 当前事务必须运行在事务内部,如果没有正在运行的事务,则会抛出异常
   * never 当前的方法不应该运行在事务中,如果有运行的事务,就抛出异常
   * nested 如果有事务在运行,当前的方法就应该在这个事务的嵌套事务内运行,否则,就启动一个新的事务,并在他自己的事务内运行
   * ioslation: 事务隔离级别
   * 1.	脏读: 对于两个事务t1 t2,t1读取了已经被t2更新但还没有被提交的字段,若t2回滚,t1读取的内容就是无效的
   * 2.	不可重复度:对于t1 t2,t1读取了一个字段,然后t2更新了该字段,之后t1再次读取同一个字段,值就不同了
   * 3.	幻读:对于两个事务t1 t2,t1从表中读取了一个字段,然后t2在表中插入了一些新的行,之后如果t1再次读取同一个表,就会多出几行
   * 1.	read uncommited(读未提交事务):允许事务读取被其他事务提交的变更,脏读,不可重复度,幻读都会出现
   * 2.	read commit(读已提交事务) :只允许事务读取已经被其他事务提交的变更,可以避免脏读,但不可重复度和幻读问题仍有可能出现
   * 3.	repeatable read(可重复读):确保事务可以多次从一个字段中读取相同的值,但这个事务持续期间,禁止其他事务对这个字段进行更新,可以避免脏读和不可重复度,但幻读仍然存在
   * 4.	serializable(串行化):确保事务可以从一个表中读取相同的行,在这个事务持续期间,禁止其他事务对该表执行插入,更新和删除操作,所有并发问题都可以解决
   *//*

  @Bean
  public PlatformTransactionManager transactionManager(
      @Qualifier("druidDataSource") DruidDataSource druidDataSource) throws PropertyVetoException {
    return new DataSourceTransactionManager(druidDataSource);
  }




  @Bean
  @ConfigurationProperties(prefix = "mybatis-plus")
  public MybatisSqlSessionFactoryBean sqlSessionFactory() throws PropertyVetoException {
    MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
    sqlSessionFactoryBean.setConfiguration(configuration());
    sqlSessionFactoryBean.setDataSource(druidDataSource);
    //注册分页插件
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setDialectType("mysql");
    //注册乐观锁插件
    OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
    //sql效率执行插件
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();

    sqlSessionFactoryBean
        .setPlugins(new Interceptor[]{paginationInterceptor, optimisticLockerInterceptor});

    sqlSessionFactoryBean.setGlobalConfig(globalConfig());

    if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
      sqlSessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
    }
    if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
      sqlSessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
      sqlSessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
    }
    return sqlSessionFactoryBean;
  }


  //全局配置策略
  @Bean
  public GlobalConfiguration globalConfig() {
    GlobalConfiguration configuration = new GlobalConfiguration();
    configuration.setIdType(IdType.AUTO.getKey());
    configuration.setDbColumnUnderline(false);
    return configuration;
  }


  */
/**
   * mybatis的配置策略
   *//*

  @Bean
  public MybatisConfiguration configuration() {
    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setMapUnderscoreToCamelCase(false);
    configuration.setLogImpl(StdOutImpl.class);
    configuration.setCacheEnabled(true);
    return configuration;
  }

}
*/
