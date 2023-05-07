package com.example.pricecompareredis.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Keyword {

	private String keyword;
	private List<ProductGroup> productGroupList;
}
