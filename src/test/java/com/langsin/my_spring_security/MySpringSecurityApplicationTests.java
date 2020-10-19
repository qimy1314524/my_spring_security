package com.langsin.my_spring_security;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.langsin.my_spring_security.dao.mapper.CourseMapper;
import com.langsin.my_spring_security.dao.mapper.StatueMapper;
import com.langsin.my_spring_security.dao.mapper.User02Mapper;
import com.langsin.my_spring_security.dao.mapper.UserMapper;
import com.langsin.my_spring_security.pojo.Course;
import com.langsin.my_spring_security.pojo.Statue;
import com.langsin.my_spring_security.pojo.User;
import com.langsin.my_spring_security.pojo.User02;
import com.langsin.my_spring_security.service.User02Service;
import com.langsin.my_spring_security.service.UserService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
class MySpringSecurityApplicationTests {


  @Autowired
  private ShardingRuleConfiguration shardingRuleConfiguration;
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private User02Mapper user02Mapper;

  @Autowired
  private User02Service user02Service;
  @Autowired
  private UserService userService;

  @Autowired
  private CourseMapper courseMapper;

  @Autowired
  private StatueMapper statueMapper;

  @Autowired
  private DruidDataSource masterspringsecurity;
//  @Autowired
  private DruidDataSource druidDataSource01;
//  @Autowired
  private DruidDataSource druidDataSource02;

  @Test
  public void test00002() {
    shardingRuleConfiguration.getTableRuleConfigs().stream().forEach(config -> {
      InlineShardingStrategyConfiguration tableShardingStrategyConfig = (InlineShardingStrategyConfiguration) config
          .getTableShardingStrategyConfig();
      System.out.println(tableShardingStrategyConfig.getShardingColumn());
      System.out.println(tableShardingStrategyConfig.getAlgorithmExpression());
      System.out.println(config.getKeyGeneratorConfig().getType());
      System.out.println(config.getKeyGeneratorConfig().getColumn());
      System.out.println(config.getActualDataNodes());
      System.out.println(config.getLogicTable());
      System.out.println(config.getLogicIndex());
    });
  }

  @Test
  @Transactional
  @Rollback(false)
  public void test00006() {
    Statue statue = new Statue();
//    statue.setSid();
    statue.setStatue("ping");
    statue.setValue("pong");
    Integer integer = statueMapper.deleteById(1);
    System.out.println(integer);
//    statueMapper.insert(statue);
  }


  @Test
  @Transactional
  @Rollback(false)
  public void test00005() {
    User user = userMapper.getUser("20167307");
    System.out.println(user);
  }

  @Test
  public void test00004() {
    System.out.println(masterspringsecurity.getUrl());
//    System.out.println(druidDataSource01.getUrl());
//    System.out.println(druidDataSource02.getUrl());
  }

  @Test
  @Transactional
  @Rollback(false)
  public void test00001() {
    Random random = new Random();
    for (long i =0; i <=200; i+=2) {
      Course course = new Course();
      long userid = (long)random.nextInt(100);
//    course.setCid(69939030017L);
      course.setUserId(i);
      course.setCname(UUID.randomUUID().toString().substring(10));
      course.setCstatue(new Random().nextInt(2)+"");
      Integer insert = courseMapper.addCourse(course);
      System.out.println(course.getCid());
    }
//    Course course = courseMapper.selectById(523528827024965633L);
//    System.out.println(course);
    //course2->course1
//    Course course1 = courseMapper.selectById(2L);
//    System.out.println(course1);

  }


  @Test
  @DS("default")
  void test0003() {
    User02 user02;
    String uuid = UUID.randomUUID().toString();
    user02 = new User02();
    user02.setUsername(uuid.substring(0, 5));
    user02.setPassword(uuid.substring(0, 8));
    user02.setAddress(1 + "address");
    user02.setAge(11);
    user02.setEmail(uuid.substring(0, 5) + "@qq.com");
    user02.setGender(new Random().nextInt(2));
    Integer integer = user02Service.addUser(user02);
    System.out.println(user02.getUserId());
  }

  /**
   * 619619tey
   33bb53dd6c8ce774f96d285f93a6ad7a
   8775fab16e8a8289a8e47c97006c0a89

   1555f45c252eab1c881b49486200336
   */
  /**
   * bCryptPasswordEncoder $2a$10$oVnRfVIjqoNRyQ31I.sJX.g5w8dv8WSo8FJWMXpXb/k1W8UwLBO1i
   * $2a$10$55x3VDk33g.ENrd71PRtGOD3oFuuu/R9i2vOMLCdpY4lwU510SgsK $2a$10$dQhSizrKFlALG1PyTdKTFu0t5ZeTJjcREvgzp.72lR5o6eddMhBPS
   */
  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void contextLoads() {
    /*String pass = EncodePassword.encodePassword("619619tey",5);
    System.out.println(pass);*/
    String encode = bCryptPasswordEncoder.encode("619619tey");
    System.out.println(encode);
  }

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;
  @Autowired
  private CacheManager cacheManager;


  @Test
  void testTemplement() {
    Cache user = cacheManager.getCache("user");
    Object object = (User) user.get("20167307").get();
    System.out.println(object);
  }

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  void testAbc() {
    System.out.println(rabbitTemplate);
  }

  @Test
  void testSend() {
    Map<String, Object> map = new HashMap<>();
    map.put("msg", "this is first message");
    map.put("data", Arrays.asList("田恩岳", "付璐瑶"));
//    rabbitTemplate.convertAndSend("exchange.topic","atguigu",map);
//    rabbitTemplate.convertAndSend("exchange.fanout","atguigu",map);

  }


  @Test
  void testReceive() {
    Object atguigu = rabbitTemplate.receiveAndConvert("atguigu");
    System.out.println(atguigu);

  }


  @Autowired
  private JavaMailSenderImpl javaMailSender;
  /*@Test
  public void sendMail(){
    SimpleMailMessage message=new SimpleMailMessage();

    //邮件设置
    message.setSubject("通知-今晚开会");
    message.setText("今晚6点开会,需求规格文档讨论");
    message.setTo("tianey@geovis.com.cn");
    message.setFrom("1499989399@qq.com");
    LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 56, 0, 10));
    Date date  = new Date().from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    message.setSentDate(date);
    javaMailSender.send(message);
  }*/

  /*@Test
  public void sendMimeMail() throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setText("<b style='color:red'>去吃饭吗</b>",true);
    helper.setTo("tianey@geovis.com.cn");
    helper.setFrom("1499989399@qq.com");
    helper.addAttachment("1.jpg",new File("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\7.jpg"));
    helper.addAttachment("2.txt",new File("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\ttt.txt"));
    javaMailSender.send(mimeMessage);

  }*/

  @Autowired
  private User user;


  @Test
  public void test002() {
    String name = "20";
    Map<String, String> explain = userMapper.explain(name);
    System.out.println(explain);
  }

  @Test
  void test003() {
    List<User02> list = new ArrayList<>(10000);
    Integer num = 10000000;
    User02 user02;
    for (int i = 0; i <= 1000; i++) {
      String uuid = UUID.randomUUID().toString();
      user02 = new User02();
      user02.setUsername(uuid.substring(0, 5));
      user02.setPassword(uuid.substring(0, 8));
      user02.setAddress(i + "address");
      user02.setAge(i);
      user02.setEmail(uuid.substring(0, 5) + "@qq.com");
      user02.setGender(new Random().nextInt(2));
      list.add(user02);
      if (i == 1000) {
        Integer integer = user02Mapper.batshInsert(list);
        System.out.println(integer);
      }
    }
  }

  @Test
  void test004() {
    String uuid = UUID.randomUUID().toString();
    User02 user02 = new User02();
    user02.setUsername(uuid.substring(0, 5));
    user02.setPassword(uuid.substring(0, 8));
    user02.setAddress(78746 + "address");
    user02.setAge(78746);
    user02.setEmail(uuid.substring(0, 5) + "@qq.com");
    user02.setGender(new Random().nextInt(2));
    user02Mapper.addUser(user02);
  }

  @Test
  void test005() {
    List<User02> user02s = userService.selectAllPage(2, 100);
    System.out.println(user02s);
  }

  @Test
  void test006() {
    List<User02> list = new ArrayList<>(10000000);
    Integer num = 0;
    User02 user02;
    for (int i = 0; i <= Integer.MAX_VALUE; i++) {
      String uuid = UUID.randomUUID().toString();
      user02 = new User02();
      user02.setUsername(uuid.substring(0, 5));
      user02.setPassword(uuid.substring(0, 8));
      user02.setAddress(i + "address");
      user02.setAge(i);
      user02.setEmail(uuid.substring(0, 5) + "@qq.com");
      user02.setGender(new Random().nextInt(2));
      list.add(user02);
      System.out.println(num++);
    }
  }

  @Test
  void test007() {
//    List<User02>list=new ArrayList<>(10000);
//    Integer num=10000000;
    AtomicInteger integer = new AtomicInteger(0);
    User02 user02;
    for (int i = 0; i < 200; i++) {
      String uuid = UUID.randomUUID().toString();
      user02 = new User02();
      user02.setUsername(uuid.substring(0, 5));
      user02.setPassword(uuid.substring(0, 8));
      user02.setAddress(i + "address");
      user02.setAge(i);
      user02.setEmail(uuid.substring(0, 5) + "@qq.com");
      user02.setGender(new Random().nextInt(2));
      Boolean delete = redisTemplate.delete("user" + i);
      if (!delete) {
        integer.getAndIncrement();
      }
//      redisTemplate.opsForValue().set("user"+i,user02);
    }
    System.out.println(integer);
  }

  @Test
  void test008() throws IOException {
//    List<User02>list=new ArrayList<>(10000);
//    Integer num=10000000;
    ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream("d://abc.txt"));
    for (int i = 0; i < 200; i++) {
      User02 user02 = (User02) redisTemplate.opsForValue().get("user" + i);
      fileWriter.writeObject(user02);
      System.out.println(user02);
    }
  }

  @Test
  void test009() throws IOException, ClassNotFoundException {
//    List<User02>list=new ArrayList<>(10000);
//    Integer num=10000000;
    ObjectInputStream fileWriter = new ObjectInputStream(new FileInputStream("d://abc.txt"));
    for (int i = 0; i < 200; i++) {
      Object object = fileWriter.readObject();
      System.out.println(object);
    }
  }


  @Test
  public void testset() {
    User02 user02 = new User02();
    user02.setUsername("tye");
    user02.setPassword("619619abd");
    user02.setAddress("山东济南");
    user02.setAge(22);
    user02.setEmail("1499989399@qq.com");
    user02.setGender(0);
    redisTemplate.opsForValue().set("tey", user02);
  }

  @Test
  public void testget() {
    User02 tey = (User02) redisTemplate.opsForValue().get("tey");
    System.out.println(tey);
  }


}
