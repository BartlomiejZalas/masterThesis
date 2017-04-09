package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.service.cache.ProductCategoryService;
import com.zalas.masterthesis.application.service.cache.ProductCategoryServiceCached;
import com.zalas.masterthesis.application.service.cache.ProductCategoryServiceNoCache;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.CACHE;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

@Controller
public class ProductCategoryCacheController {

    @Autowired
    private ProductCategoryServiceCached cachedService;
    @Autowired
    private ProductCategoryServiceNoCache noCacheService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryCacheController.class);

    public ProductCategoryService getService() {
        try {
            ConfigurationClient configurationClient = new ConfigurationClient();
            Value cacheComponentType = configurationClient.getConfiguration(CACHE);
            LOGGER.info("Using redundant component: " +cacheComponentType);
            return (cacheComponentType == Value.CACHED) ? cachedService : noCacheService;
        } catch (ConfigurationClientException e) {
            LOGGER.warn("Cannot get configuration for Cache component - NoCache used", e);
            return noCacheService;
        }
    }

}
