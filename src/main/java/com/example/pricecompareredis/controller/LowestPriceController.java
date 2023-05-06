package com.example.pricecompareredis.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricecompareredis.service.LowestPriceService;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

@RestController
@RequestMapping("/")
public class LowestPriceController {

	private final LowestPriceService lowestPriceService;

	public LowestPriceController(final LowestPriceService lowestPriceService) {
		this.lowestPriceService = lowestPriceService;
	}

	@GetMapping("/getZSETValue")
	Set getZSetValue(String key) {
		return lowestPriceService.getZSet(key);
	}

	@PutMapping("/product")
	int SetNewProduct(@RequestBody final Product product) {
		return lowestPriceService.setNewProduct(product);
	}

	@PutMapping("/productGroup")
	int SetNewProductGrp(@RequestBody final ProductGroup productGroup) {
		return lowestPriceService.setNewProductGrp(productGroup);
	}
}
