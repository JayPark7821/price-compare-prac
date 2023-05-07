package com.example.pricecompareredis.service;

import java.util.Set;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

public interface LowestPriceService {
	Set getZSet(String key);

	int setNewProduct(Product product);

	int setNewProductGrp(ProductGroup productGroup);

	int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);
}
