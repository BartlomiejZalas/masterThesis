package com.zalas.masterthesis.application.service.cache;

import com.zalas.masterthesis.application.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceCached implements ProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceCached.class);

    @Autowired
    private ProductCategoryServiceDirect productCategoryServiceDirect;

    @Cacheable("productCategory")
    @Override
    public ProductCategory findOne(int id) {
        LOGGER.info("findOne() cached used");
        return productCategoryServiceDirect.findOne(id);
    }

    @CacheEvict(value = "productCategory", allEntries = true)
    @Override
    public ProductCategory update(int id, String newName) {
        LOGGER.info("update() cached used");
        return productCategoryServiceDirect.update(id, newName);
    }
}
