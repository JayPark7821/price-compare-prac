package com.example.pricecompareredis.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LowestPriceServiceImpl implements LowestPriceService {

	private final RedisTemplate redisTemplate;

	public LowestPriceServiceImpl(final RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Set getZSet(final String key) {
		return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
	}
}
