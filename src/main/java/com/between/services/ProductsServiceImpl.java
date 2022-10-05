package com.between.services;

import com.between.dtos.ProductDto;
import com.between.dtos.SimilarProductsDataDto;
import com.between.infra.ProductsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);

    @Autowired
    ProductsClient productsClient;

    public SimilarProductsDataDto getSimilarProducts(long id) throws Exception {
        Long[] ids = getSimilarProductIds(id);
        AtomicInteger errorsCount = new AtomicInteger(0);

        List<ProductDto> similarProducts = Arrays.stream(ids)
                .map(productId -> {
                    try {
                        ProductDto p = getProduct(productId);
                        p.getId();
                        return p;
                    }
                    catch(Exception e) {
                        logger.error(e.getMessage());
                        errorsCount.incrementAndGet();
                        return null;
                    }
                })
                .filter(product -> product != null )
                .collect(Collectors.toUnmodifiableList());

        return SimilarProductsDataDto.builder().similarProducts(similarProducts).partialContext(errorsCount.get() != 0).build();
    }

    /**
     * Returns a product by id
     * @param id
     * @return
     * @throws Exception
     */
    private ProductDto getProduct(long id) throws Exception {
        ProductDto p = productsClient.getProduct(id);
        return p;
    }

    /**
     * Returns a list of similar product ids for a given product
     * @param id
     * @return
     * @throws Exception
     */
    private Long[] getSimilarProductIds(long id) throws Exception {
        return productsClient.getSimilarProductIds(id);
    }
}
