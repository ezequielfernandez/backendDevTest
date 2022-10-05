package com.between.infra;

import com.between.dtos.ProductDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.between.config.CacheConfig.PRODUCTS_CACHE;

@Component
public class ProductsClientImpl implements ProductsClient {
    private RestTemplate restTemplate;
    private static final String BASE_PATH = "http://localhost:3001/";

    public ProductsClientImpl() {
        restTemplate = new RestTemplate();
    }

    @Cacheable(cacheNames = {PRODUCTS_CACHE}, key = "#productId")
    public ProductDto getProduct(Long productId) throws RestClientException {
        String uri = BASE_PATH + String.format("/product/%d", productId);

        return restTemplate.getForObject(uri, ProductDto.class);
    }

    public Long[] getSimilarProductIds(Long productId) throws RestClientException {
        String uri = BASE_PATH + String.format("/product/%d/similarids", productId);

        return restTemplate.getForObject(uri, Long[].class);
    }
}
