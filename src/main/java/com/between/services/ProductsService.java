package com.between.services;

import com.between.dtos.SimilarProductsDataDto;

public interface ProductsService {
    SimilarProductsDataDto getSimilarProducts(long id) throws Exception;
}
