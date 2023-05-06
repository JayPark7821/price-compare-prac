package com.example.pricecompareredis.service;

import java.util.Set;

import com.example.pricecompareredis.vo.Product;

public interface LowestPriceService {
	Set getZSet(String key);

	int setNewProduct(Product product);
}
