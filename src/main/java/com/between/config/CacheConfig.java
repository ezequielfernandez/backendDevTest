package com.between.config;

import com.between.services.ProductsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfig {

    public static final String PRODUCTS_CACHE = "productsCache";
    public static final int MINUTES = 60;
    Logger logger = LoggerFactory.getLogger(ProductsServiceImpl.class);

    @Async
    @CacheEvict(allEntries = true, value = {PRODUCTS_CACHE})
    @Scheduled(fixedRate = MINUTES * 60 * 1000)
    public void reportCacheEvict() {
        logger.info("Flushing cache");
    }
}