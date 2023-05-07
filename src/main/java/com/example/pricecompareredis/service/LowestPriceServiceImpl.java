package com.example.pricecompareredis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public int setNewProductGrpToKeyword(final String keyword, final String prodGrpId, final double score) {
		redisTemplate.opsForSet().add(keyword, prodGrpId, score);
		return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();
	}

	@Override
	public Keyword getLowestPriceProductKeyword(final String keyword) {
		Keyword returnInfo = new Keyword();
		List<ProductGroup> tempProdGrp = new ArrayList<>();
		// keyword 를 통해 ProductGroup 가져오기 (10개)
		tempProdGrp = getProductGrpUsingKeyword(keyword);

		// 가져온 정보들을 Return 할 Object 에 넣기
		returnInfo.setKeyword(keyword);
		returnInfo.setProductGroupList(tempProdGrp);
		// 해당 Object return
		return returnInfo;
	}

	List<ProductGroup> getProductGrpUsingKeyword(final String keyword) {
		List<ProductGroup> returnInfo = new ArrayList<>();

		// input 받은 keyword 로 productGrpId를 조회
		List<String> prodGrpIdList = new ArrayList<>();
		prodGrpIdList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));
		//Product tempProduct = new Product();
		List<Product> tempProdList = new ArrayList<>();

		//10개 prodGrpId로 loop
		for (final String prodGrpId : prodGrpIdList) {
			// Loop 타면서 ProductGrpID 로 Product:price 가져오기 (10개)

			ProductGroup tempProdGrp = new ProductGroup();

			Set prodAndPriceList = new HashSet();
			prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
			Iterator<Object> prodPricObj = prodAndPriceList.iterator();

			// loop 타면서 product obj에 bind (10개)
			while (prodPricObj.hasNext()) {
				ObjectMapper objMapper = new ObjectMapper();
				// {"value":00-10111-}, {"score":11000}
				Map<String, Object> prodPriceMap = objMapper.convertValue(prodPricObj.next(), Map.class);
				Product tempProduct = new Product();
				// Product Obj bind
				tempProduct.setProductId(prodPriceMap.get("value").toString()); // prod_id
				tempProduct.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue()); //es 검색된 score
				tempProduct.setProdGrpId(prodGrpId);

				tempProdList.add(tempProduct);
			}
			// 10개 product price 입력완료
			tempProdGrp.setProductGroupId(prodGrpId);
			tempProdGrp.setProductList(tempProdList);
			returnInfo.add(tempProdGrp);
		}

		return returnInfo;
	}

}
