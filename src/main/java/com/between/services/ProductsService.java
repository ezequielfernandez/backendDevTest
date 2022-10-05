package com.between.services;


import com.between.dtos.ProductDto;

import java.util.List;

public interface ProductsService {
    List<ProductDto> getSimilarProducts(long id) throws Exception;
}
