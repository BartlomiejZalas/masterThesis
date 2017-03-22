package com.zalas.masterthesis.application.service.productcategory;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceCached implements ProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceCached.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Cacheable("productCategory")
    public ProductCategory findOne(int id) {
        LOGGER.info("findOne() cached used");
        simulateSlowService();
        return productCategoryRepository.findOne(id);
    }

    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
