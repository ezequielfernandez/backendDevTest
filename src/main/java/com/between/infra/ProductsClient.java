package com.between.infra;

import com.between.dtos.ProductDto;

import java.util.List;

public interface ProductsClient {
    ProductDto getProduct(Long productId) throws Exception;

    List<Long> getSimilarProductIds(Long productId) throws Exception;
}
