package com.cxn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
@Order(value=Integer.MIN_VALUE+3)
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisConfig {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory  redisConnectionFactory){
		RedisTemplate<String, Object>redisTemplate =new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 设置redisTemplate的序列化器，默认使用的是原生Java，
		// 因为在redis上存放Object对象是通过对象的序列化方式实现的，所以如果使用redisTemplate存放pojo类的话，必须实现serializable接口
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setEnableTransactionSupport(false);
		System.out.println("redisTemplate初始化成功");
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory  redisConnectionFactory){
		StringRedisTemplate stringRedisTemplate =new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setEnableTransactionSupport(false);
		return stringRedisTemplate;
	}
}
