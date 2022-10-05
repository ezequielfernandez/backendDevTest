package com.between.services;

import com.between.dtos.ProductDto;
import com.between.dtos.SimilarProductsDataDto;
import com.between.infra.ProductsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);

    @Autowired
    ProductsClient productsClient;

    public SimilarProductsDataDto getSimilarProducts(long id) throws Exception {
        List<Long> ids = getSimilarProductIds(id);
        AtomicInteger errorsCount = new AtomicInteger(0);

        List<ProductDto> similarProducts = ids.stream()
                .map(productId -> {
                    try {
                        return getProduct(productId);
                    }
                    catch(Exception e) {
                        logger.error(e.getMessage());
                        errorsCount.incrementAndGet();
                        return null;
                    }
                })
                .filter(product -> product != null )
                .collect(Collectors.toList());

        return SimilarProductsDataDto.builder().similarProducts(similarProducts).partialContext(errorsCount.get() != 0).build();
    }

    /**
     * Returns a product by id
     * @param id
     * @return
     * @throws Exception
     */
    private ProductDto getProduct(long id) throws Exception {
        return productsClient.getProduct(id);
    }

    /**
     * Returns a list of similar product ids for a given product
     * @param id
     * @return
     * @throws Exception
     */
    private List<Long> getSimilarProductIds(long id) throws Exception {
        return productsClient.getSimilarProductIds(id);
    }
}
