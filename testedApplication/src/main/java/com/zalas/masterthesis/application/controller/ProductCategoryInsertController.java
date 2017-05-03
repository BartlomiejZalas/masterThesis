package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceBatched;
import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceDirect;
import com.zalas.masterthesis.application.service.creator.ProductCreatorService;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.BATCH;
import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

@Controller
public class ProductCategoryInsertController {

    @Autowired
    private ProductCategoryCreatorServiceBatched productCategoryCreatorServiceBatched;
    @Autowired
    private ProductCategoryCreatorServiceDirect productCategoryCreatorServiceDirect;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryInsertController.class);

    public ProductCreatorService getService() {
        try {
            ConfigurationClient configurationClient = new ConfigurationClient();
            Value batchComponentType = configurationClient.getConfiguration(BATCH);
            ProductCreatorService productCreatorService = (batchComponentType == Value.BATCHED) ? productCategoryCreatorServiceBatched : productCategoryCreatorServiceDirect;
            LOGGER.info("Used redundant component: " + productCreatorService.getClass().getSimpleName());
            return productCreatorService;
        } catch (ConfigurationClientException e) {
            LOGGER.warn("Cannot get configuration for Batch component - Direct used", e);
            return productCategoryCreatorServiceDirect;
        }
    }
}
