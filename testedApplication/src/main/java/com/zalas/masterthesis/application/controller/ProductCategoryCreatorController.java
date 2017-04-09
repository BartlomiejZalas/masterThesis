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
public class ProductCategoryCreatorController {

    @Autowired
    private ProductCategoryCreatorServiceBatched productCategoryCreatorServiceBatched;
    @Autowired
    private ProductCategoryCreatorServiceDirect productCategoryCreatorServiceDirect;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryCreatorController.class);

    public ProductCreatorService getService() {
        try {
            ConfigurationClient configurationClient = new ConfigurationClient();
            Value batchComponentType = configurationClient.getConfiguration(BATCH);
            return (batchComponentType == Value.BATCHED) ? productCategoryCreatorServiceBatched : productCategoryCreatorServiceDirect;
        } catch (ConfigurationClientException e) {
            LOGGER.warn("Cannot get configuration for Batch component - Direct used", e);
            return productCategoryCreatorServiceDirect;
        }
    }
}
