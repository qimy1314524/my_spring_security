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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.shardingsphere.api.config.encryptor.EncryptRuleConfiguration;
import org.apache.shardingsphere.api.config.encryptor.EncryptorRuleConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author tey
 * @version V1.0
 * @date 2020/10/12- 9:29
 * @desc
 **/
@Configuration
@EnableTransactionManagement
public class MyShardingSphereConfiguration {


  @Autowired
  private MybatisProperties properties;


  @Autowired
  private Environment environment;

  @Autowired
  private SpringBootShardingRuleConfigurationProperties springBootShardingRuleConfigurationProperties;

  @Autowired
  private TableRuleConfiguration tableRuleConfiguration;


  /*@Bean
  @ConfigurationProperties("spring.shardingsphere.sharding")
  public SpringBootShardingRuleConfigurationProperties ShardingRuleConfigurationProperties() {
    return new SpringBootShardingRuleConfigurationProperties();
  }*/

  @Bean
  @ConfigurationProperties("mybatis")
  public MybatisProperties properties() {
    return new MybatisProperties();
  }

  /*@Bean
  public TableRuleConfiguration user02TableRuleConfiguration() {
    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("user02",
        "default.user02");
    ShardingStrategyConfiguration shardingDatabaseStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "userId", "default");
    tableRuleConfiguration.setDatabaseShardingStrategyConfig(shardingDatabaseStrategyConfiguration);
    ShardingStrategyConfiguration shardingStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "userId", "user02");
    tableRuleConfiguration.setTableShardingStrategyConfig(shardingStrategyConfiguration);
    *//*KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("SNOWFLAKE",
        "");
    tableRuleConfiguration.setKeyGeneratorConfig(keyGeneratorConfiguration);*//*
    return tableRuleConfiguration;
  }*/

  /*@Bean
  public TableRuleConfiguration userTableRuleConfiguration() {
    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("user",
        "masterSpringSecurity.user,slaveSpringSecurity");
    ShardingStrategyConfiguration shardingDatabaseStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "userId", "default");
    tableRuleConfiguration.setDatabaseShardingStrategyConfig(shardingDatabaseStrategyConfiguration);
    ShardingStrategyConfiguration shardingStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "userId", "user");
    tableRuleConfiguration.setTableShardingStrategyConfig(shardingStrategyConfiguration);
    *//*KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("SNOWFLAKE",
        "");
    tableRuleConfiguration.setKeyGeneratorConfig(keyGeneratorConfiguration);*//*
    return tableRuleConfiguration;
  }*/


  @Bean
  public TableRuleConfiguration tableRuleConfiguration() {
    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("course",
        "mastercourse1.course1,mastercourse1.course2,mastercourse2.course1,mastercourse2.course2");
    tableRuleConfiguration.setLogicIndex("course");
    ShardingStrategyConfiguration shardingDatabaseStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "userId", "mastercourse$->{userId % 2 + 1}");
    tableRuleConfiguration.setDatabaseShardingStrategyConfig(shardingDatabaseStrategyConfiguration);
    ShardingStrategyConfiguration shardingStrategyConfiguration = new InlineShardingStrategyConfiguration(
        "cid", "course$->{cid % 2 + 1}");
    tableRuleConfiguration.setTableShardingStrategyConfig(shardingStrategyConfiguration);
    KeyGeneratorConfiguration keyGeneratorConfiguration = new KeyGeneratorConfiguration("SNOWFLAKE",
        "cid");
    tableRuleConfiguration.setKeyGeneratorConfig(keyGeneratorConfiguration);
    return tableRuleConfiguration;
  }

  @Bean
  public Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfiguration() {

    Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigurations = new ArrayList<>();
    MasterSlaveRuleConfiguration masterCourse1RuleConfiguration = new MasterSlaveRuleConfiguration(
        "mastercourse1", "mastercourse1",
        Arrays.asList("slavecourse1"));

    MasterSlaveRuleConfiguration masterCourse2RuleConfiguration = new MasterSlaveRuleConfiguration(
        "mastercourse2", "mastercourse2",
        Arrays.asList("slavecourse2"));

    MasterSlaveRuleConfiguration masterSpringSecurityRuleConfiguration = new MasterSlaveRuleConfiguration(
        "masterspringsecurity", "masterspringsecurity",
        Arrays.asList("slavespringsecurity"));
    masterSlaveRuleConfigurations.add(masterCourse2RuleConfiguration);
    masterSlaveRuleConfigurations.add(masterCourse1RuleConfiguration);
    masterSlaveRuleConfigurations.add(masterSpringSecurityRuleConfiguration);
    return masterSlaveRuleConfigurations;
  }

  /*@Bean
  public EncryptRuleConfiguration encryptRuleConfiguration(){
    EncryptRuleConfiguration encryptRuleConfiguration=new EncryptRuleConfiguration();
    Properties properties = new Properties();
    properties.setProperty("aes.key.value","123456abc");
    EncryptorRuleConfiguration encryptorRuleConfiguration=new EncryptorRuleConfiguration("aes","masterspringsecurity.slavespringsecurity",properties);
    return encryptRuleConfiguration;
  }*/


  /*@Bean
  public TableRuleConfiguration defaultTableRuleConfiguration() {
    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("",
        "");
    tableRuleConfiguration.setLogicIndex("course");
    ShardingStrategyConfiguration shardingDatabaseStrategyConfiguration=new InlineShardingStrategyConfiguration("userId","course_$->{userId % 2 + 1}");
    tableRuleConfiguration.setDatabaseShardingStrategyConfig(shardingDatabaseStrategyConfiguration);
    ShardingStrategyConfiguration shardingStrategyConfiguration=new InlineShardingStrategyConfiguration("cid","course$->{cid % 2 + 1}");
    tableRuleConfiguration.setTableShardingStrategyConfig(shardingStrategyConfiguration);
    KeyGeneratorConfiguration keyGeneratorConfiguration=new KeyGeneratorConfiguration("SNOWFLAKE","cid");
    tableRuleConfiguration.setKeyGeneratorConfig(keyGeneratorConfiguration);
    return tableRuleConfiguration;
  }*/


  @Bean
  @ConfigurationProperties("spring.shardingsphere.sharding")
  public ShardingRuleConfiguration shardingRuleConfiguration() {
    ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();

    shardingRuleConfiguration.setEncryptRuleConfig(null);

    //设置默认数据源
    shardingRuleConfiguration.setDefaultDataSourceName("masterspringsecurity");
    //设置公共表
    //也可以给这个表设置TableRuleConfiguration
    shardingRuleConfiguration.getBroadcastTables().add("statue");
    shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
    shardingRuleConfiguration.getMasterSlaveRuleConfigs().addAll(masterSlaveRuleConfiguration());
    //设置了默认数据源可不进行特定数据源的配置
    /*shardingRuleConfiguration.getTableRuleConfigs().add(user02TableRuleConfiguration());
    shardingRuleConfiguration.getTableRuleConfigs().add(userTableRuleConfiguration());*/
    return shardingRuleConfiguration;
  }

  /*@Bean
  @ConfigurationProperties("spring.shardingsphere.sharding")
  public MasterSlaveRuleConfiguration masterSlaveRuleConfiguration() {
    ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
    //设置默认数据源
    shardingRuleConfiguration.setDefaultDataSourceName("masterSpringSecurity");
    //设置公共表
    //也可以给这个表设置TableRuleConfiguration
    shardingRuleConfiguration.getBroadcastTables().add("statue");
    shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
    shardingRuleConfiguration.setMasterSlaveRuleConfigs(masterSlaveRuleConfiguration());
    //设置了默认数据源可不进行特定数据源的配置
    *//*shardingRuleConfiguration.getTableRuleConfigs().add(user02TableRuleConfiguration());
    shardingRuleConfiguration.getTableRuleConfigs().add(userTableRuleConfiguration());*//*
    return shardingRuleConfiguration;
  }*/



  @Bean("masterspringsecurity")
  @ConfigurationProperties("spring.shardingsphere.datasource.masterspringsecurity")
  public DruidDataSource druidDataSourceMasterSpringSecurity() {
    return new DruidDataSource();
  }

  @Bean("mastercourse1")
  @ConfigurationProperties("spring.shardingsphere.datasource.mastercourse1")
  public DruidDataSource druidDataSourceMasterCourse1() {
    return new DruidDataSource();
  }

  @Bean("mastercourse2")
  @ConfigurationProperties("spring.shardingsphere.datasource.mastercourse2")
  public DruidDataSource druidDataSourceMasterCourse2() {
    return new DruidDataSource();
  }

  @Bean("slavespringsecurity")
  @ConfigurationProperties("spring.shardingsphere.datasource.slavespringsecurity")
  public DruidDataSource druidDataSourceSlaveSpringSecurity() {
    return new DruidDataSource();
  }

  @Bean("slavecourse1")
  @ConfigurationProperties("spring.shardingsphere.datasource.slavecourse1")
  public DruidDataSource druidDataSourceSlaveCourse1() {
    return new DruidDataSource();
  }

  @Bean("slavecourse2")
  @ConfigurationProperties("spring.shardingsphere.datasource.slavecourse2")
  public DruidDataSource druidDataSourceSlaveCourse2() {
    return new DruidDataSource();
  }


  @Bean
  public DataSource shardingDataSource() {
    Properties configProperties = new Properties();
    configProperties.setProperty("sql.show","true");
    configProperties.setProperty("query.with.cipher.column","true");
    Map<String, DataSource> dataSourceMap = new HashMap<>();
    dataSourceMap.put("masterspringsecurity", druidDataSourceMasterSpringSecurity());
    dataSourceMap.put("mastercourse1", druidDataSourceMasterCourse1());
    dataSourceMap.put("mastercourse2", druidDataSourceMasterCourse2());
    dataSourceMap.put("slavespringsecurity", druidDataSourceSlaveSpringSecurity());
    dataSourceMap.put("slavecourse1", druidDataSourceSlaveCourse1());
    dataSourceMap.put("slavecourse2", druidDataSourceSlaveCourse2());
    DataSource dataSource = null;
    try {
      dataSource = ShardingDataSourceFactory
          .createDataSource(dataSourceMap, shardingRuleConfiguration(), configProperties);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dataSource;
  }


  /**
   * @throws PropertyVetoException 事务传播行为: required 如果有事务在运行,当前的方法就在这个事务内运行,否则就启动一个新的事物,并在自己的事务内运行
   * required_new 当前的方法必须启动新的事务,并在他自己的事务内运行,如果有事务正在运行,应该将它挂起 supports
   * 如果有事务在运行,当前的方法就在这个事务内运行,否则他可以不运行在事务内 not_support 当前的方法不应该运行在事务中,如果有事务,就将它挂起 mandatory
   * 当前事务必须运行在事务内部,如果没有正在运行的事务,则会抛出异常 never 当前的方法不应该运行在事务中,如果有运行的事务,就抛出异常 nested
   * 如果有事务在运行,当前的方法就应该在这个事务的嵌套事务内运行,否则,就启动一个新的事务,并在他自己的事务内运行 ioslation: 事务隔离级别 1.	脏读: 对于两个事务t1
   * t2,t1读取了已经被t2更新但还没有被提交的字段,若t2回滚,t1读取的内容就是无效的 2.	不可重复度:对于t1 t2,t1读取了一个字段,然后t2更新了该字段,之后t1再次读取同一个字段,值就不同了
   * 3.	幻读:对于两个事务t1 t2,t1从表中读取了一个字段,然后t2在表中插入了一些新的行,之后如果t1再次读取同一个表,就会多出几行 1.	read
   * uncommited(读未提交事务):允许事务读取被其他事务提交的变更,脏读,不可重复度,幻读都会出现 2.	read commit(读已提交事务)
   * :只允许事务读取已经被其他事务提交的变更,可以避免脏读,但不可重复度和幻读问题仍有可能出现 3.	repeatable read(可重复读):确保事务可以多次从一个字段中读取相同的值,但这个事务持续期间,禁止其他事务对这个字段进行更新,可以避免脏读和不可重复度,但幻读仍然存在
   * 4.	serializable(串行化):确保事务可以从一个表中读取相同的行,在这个事务持续期间,禁止其他事务对该表执行插入,更新和删除操作,所有并发问题都可以解决
   * XAShardingTransactionManager 两阶段事务
   * SagaShardingTransactionMananger saga柔性事务
   * seataShardingTransactionManager seata柔性事务
   *使用@ShardingTransactionType(TransactionType.XA) 支持TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE来选择分布式事务
   */
  @Bean
  public PlatformTransactionManager transactionManager(
      @Qualifier("shardingDataSource") DataSource shardingDataSource) {
    return new DataSourceTransactionManager(shardingDataSource);
  }



 /* @Bean
  public SqlSessionFactory sessionFactory(DataSource shardingDataSource) throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(shardingDataSource);
    sessionFactory.setTypeAliasesPackage("com.plf.learn.sharding.bean");
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setMapUnderscoreToCamelCase(true);
    sessionFactory.setConfiguration(configuration);
    return sessionFactory.getObject();
  }*/


  @Bean("sqlSessionFactory")
  @ConfigurationProperties(prefix = "mybatis-plus")
  public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
    sqlSessionFactoryBean.setConfiguration(configuration());
    sqlSessionFactoryBean.setDataSource(shardingDataSource());
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
    sqlSessionFactoryBean.setConfigurationProperties(properties.getConfigurationProperties());
    if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
      sqlSessionFactoryBean.setTypeAliasesPackage(properties.getTypeAliasesPackage());
    }
    if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
      sqlSessionFactoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
      sqlSessionFactoryBean.setMapperLocations(properties.resolveMapperLocations());
    }
    return sqlSessionFactoryBean;
  }

  /*@Bean("defaultSqlSessionFactory")
  @Primary
  public MybatisSqlSessionFactoryBean defaultSqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
    sqlSessionFactoryBean.setConfiguration(configuration());
    sqlSessionFactoryBean.setDataSource(druidDataSourceDefault());
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
    sqlSessionFactoryBean.setConfigurationProperties(properties.getConfigurationProperties());
    sqlSessionFactoryBean.setTypeAliasesPackage("com.langsin.my_spring_security.pojo.primary");
    if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
      sqlSessionFactoryBean.setTypeHandlersPackage(properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
      sqlSessionFactoryBean.setMapperLocations(properties.resolveMapperLocations());
    }
    return sqlSessionFactoryBean;
  }
*/

  //全局配置策略
  @Bean
  public GlobalConfiguration globalConfig() {
    GlobalConfiguration configuration = new GlobalConfiguration();
    configuration.setIdType(IdType.AUTO.getKey());
    configuration.setDbColumnUnderline(false);
    return configuration;
  }


  /**
   * mybatis的配置策略
   */
  @Bean
  public MybatisConfiguration configuration() {
    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setMapUnderscoreToCamelCase(false);
    configuration.setLogImpl(StdOutImpl.class);
    configuration.setCacheEnabled(true);
    return configuration;
  }


}
