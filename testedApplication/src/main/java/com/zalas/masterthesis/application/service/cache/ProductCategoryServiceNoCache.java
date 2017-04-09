package com.zalas.masterthesis.application.service.cache;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceNoCache implements ProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceNoCache.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategory findOne(int id) {
        LOGGER.info("findOne() direct used");
        simulateSlowService();
        return productCategoryRepository.findOne(id);
    }

    @Override
    public ProductCategory update(int id, String newName) {
        ProductCategory productCategory = productCategoryRepository.findOne(id);
        if (productCategory == null) {
            return null;
        }
        productCategory.setName(newName);
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        LOGGER.info("productCategory updated");
        return savedCategory;
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
