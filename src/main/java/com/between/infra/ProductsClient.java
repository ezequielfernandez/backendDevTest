package com.between.infra;

import com.between.dtos.ProductDto;

public interface ProductsClient {
    ProductDto getProduct(Long productId) throws Exception;

    Long[] getSimilarProductIds(Long productId) throws Exception;
}
