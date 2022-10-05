package com.between.services;

import com.between.dtos.ProductDto;
import com.between.infra.ProductsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);

    @Autowired
    ProductsClient productsClient;

    public List<ProductDto> getSimilarProducts(long id) throws Exception {
        List<Long> ids = getSimilarProductIds(id);

        List<ProductDto> similarProducts = ids.stream()
                .map(productId -> {
                    try {
                        return getProduct(productId);
                    }
                    catch(Exception e) {
                        logger.error(e.getMessage());
                        return null;
                    }
                })
                .filter(product -> product != null )
                .collect(Collectors.toList());
        return similarProducts;
    }

    private ProductDto getProduct(long id) throws Exception {
        return productsClient.getProduct(id);
    }

    private List<Long> getSimilarProductIds(long id) throws Exception {
        return productsClient.getSimilarProductIds(id);
    }

}
