package com.example.pricecompareredis.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

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

	@Override
	public int setNewProduct(final Product product) {
		redisTemplate.opsForZSet().add(product.getProdGrpId(), product.getProductId(), product.getPrice());
		return redisTemplate.opsForZSet().rank(product.getProdGrpId(), product.getProductId()).intValue();
	}

	@Override
	public int setNewProductGrp(final ProductGroup productGroup) {
		final List<Product> productList = productGroup.getProductList();
		redisTemplate.opsForZSet().add(
			productGroup.getProductGroupId(),
			productList.get(0).getProductId(),
			productList.get(0).getPrice()
		);
		return redisTemplate.opsForZSet().zCard(productGroup.getProductGroupId()).intValue();
	}

	@Override
	public int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score) {
		redisTemplate.opsForSet().add(keyword, prodGrpId, score);
		return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();
	}
}
