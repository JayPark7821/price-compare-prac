package com.example.pricecompareredis.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricecompareredis.service.LowestPriceService;

@RestController
@RequestMapping("/")
public class LowestPriceController {

	private final LowestPriceService lowestPriceService;

	public LowestPriceController(final LowestPriceService lowestPriceService) {
		this.lowestPriceService = lowestPriceService;
	}

	@GetMapping("/getZSETValue")
	Set getZSetValue(String key) {
		return null;
	}
}
