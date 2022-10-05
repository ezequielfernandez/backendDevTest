package com.between.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SimilarProductsDataDto {
    List<ProductDto> similarProducts;
    boolean partialContext;
}
