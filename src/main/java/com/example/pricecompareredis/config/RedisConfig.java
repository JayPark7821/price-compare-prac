package com.example.pricecompareredis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	private final RedisProperties redisProperties;

	public RedisConfig(final RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}

	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
	}

	@Bean
	@Primary
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}
