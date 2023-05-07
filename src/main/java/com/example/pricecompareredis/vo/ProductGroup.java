package com.example.pricecompareredis.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductGroup {

	private String productGroupId;
	private List<Product> productList;
}
