package com.langsin.my_spring_security.config.redis;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Objects;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

/**
 * @author tey
 * @version V1.0
 * @date 2020/7/23- 11:19
 * @desc
 **/
@Configuration
public class RedisConfig {


  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
      throws UnknownHostException {
    RedisTemplate<Object, Object> template = new RedisTemplate();
    template.setConnectionFactory(redisConnectionFactory);
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
        Object.class);
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
    template.setDefaultSerializer(genericJackson2JsonRedisSerializer);
    return template;
  }

  @Bean
  @Primary
  public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
    RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration
        .defaultCacheConfig()
        // 设置key为String
        .serializeKeysWith(RedisSerializationContext.SerializationPair
            .fromSerializer(redisTemplate.getStringSerializer()))
        // 设置value 为自动转Json的Object
        .serializeValuesWith(RedisSerializationContext.SerializationPair
            .fromSerializer(redisTemplate.getValueSerializer()))
        // 不缓存null
        .disableCachingNullValues()
        // 缓存数据保存1小时
//        .entryTtl(Duration.ofSeconds(5))
        ;
    return RedisCacheManager.RedisCacheManagerBuilder
        // Redis 连接工厂
        .fromConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()))
        // 缓存配置
        .cacheDefaults(defaultCacheConfiguration)
        // 配置同步修改或删除 put/evict
        .transactionAware()
        .build();
  }

  @Bean("ttlCacheManager")
  public CacheManager ttlCacheManager(RedisTemplate<Object, Object> redisTemplate) {
    RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
        .defaultCacheConfig()
        .serializeKeysWith(SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
        .serializeValuesWith(SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
        .entryTtl(Duration.ofHours(2));
    RedisCacheManager cacheManager =
        RedisCacheManagerBuilder
            .fromConnectionFactory(redisTemplate.getConnectionFactory())
            .transactionAware()
            .cacheDefaults(cacheConfiguration)
            .build();
    return cacheManager;
  }


}
