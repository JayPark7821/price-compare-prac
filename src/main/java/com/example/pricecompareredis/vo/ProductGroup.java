package com.example.pricecompareredis.vo;

import java.util.List;

import lombok.Getter;

@Getter
public class ProductGroup {

	private String productGroupId;
	private List<Product> productList;
}
