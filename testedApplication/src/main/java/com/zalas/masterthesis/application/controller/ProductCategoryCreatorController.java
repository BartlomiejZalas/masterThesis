package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceBatched;
import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceDirect;
import com.zalas.masterthesis.application.service.creator.ProductCreatorService;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClient;
import com.zalas.masterthesis.configurationserver.api.client.ConfigurationClientException;
import com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.*;

@Controller
public class ProductCategoryCreatorController {

    @Autowired
    private ProductCategoryCreatorServiceBatched productCategoryCreatorServiceBatched;
    @Autowired
    private ProductCategoryCreatorServiceDirect productCategoryCreatorServiceDirect;

    public ProductCreatorService getService() throws ConfigurationClientException {
            ConfigurationClient configurationClient = new ConfigurationClient();
            Value batchComponentType = configurationClient.getConfiguration(BATCH);
            return (batchComponentType == Value.BATCHED) ? productCategoryCreatorServiceBatched : productCategoryCreatorServiceDirect;
    }
}
