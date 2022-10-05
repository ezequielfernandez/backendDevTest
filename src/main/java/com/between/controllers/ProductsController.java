package com.between.controllers;

import com.between.dtos.ProductDto;
import com.between.dtos.SimilarProductsDataDto;
import com.between.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Api(value = "Products controller")
@RestController
public class ProductsController extends DefaultResponseErrorHandler {
    @Autowired
    ProductsService productsService;

    @ApiOperation(value = "Get list of similar product ids", response = List.class)
    @GetMapping("/product/{productId}/similar")
    public ResponseEntity<?> getSimilarProducts(@PathVariable("productId") long id) throws Exception {
        SimilarProductsDataDto productsData = productsService.getSimilarProducts(id);
        List<ProductDto> products = productsData.getSimilarProducts();

        if (productsData.isPartialContext()) {
            return new ResponseEntity(products, HttpStatus.PARTIAL_CONTENT);
        }

        return ResponseEntity.ok(products);
    }
}
