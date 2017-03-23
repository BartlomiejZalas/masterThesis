package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceBatched;
import com.zalas.masterthesis.application.service.creator.ProductCategoryCreatorServiceDirect;
import com.zalas.masterthesis.application.service.creator.ProductCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductCategoryCreatorController {

    @Autowired
    private ProductCategoryCreatorServiceBatched productCategoryCreatorServiceBatched;
    @Autowired
    private ProductCategoryCreatorServiceDirect productCategoryCreatorServiceDirect;

    public static final boolean BATCHED_INSERT_ENABLED = false;

    public ProductCreatorService getService() {
        return BATCHED_INSERT_ENABLED ? productCategoryCreatorServiceBatched : productCategoryCreatorServiceDirect;
    }
}
