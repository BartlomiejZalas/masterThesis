package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.service.productcategory.ProductCategoryService;
import com.zalas.masterthesis.application.service.productcategory.ProductCategoryServiceCached;
import com.zalas.masterthesis.application.service.productcategory.ProductCategoryServiceDirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductCategoryServiceController {

    @Autowired
    private ProductCategoryServiceCached cachedService;
    @Autowired
    private ProductCategoryServiceDirect directService;

    private static final boolean CACHE_ENABLED = true;

    public ProductCategoryService getService() {
        return CACHE_ENABLED ? cachedService : directService;
    }

}
