package com.zalas.masterthesis.application.service.creator;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductCategoryCreatorServiceDirect implements ProductCreatorService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private DummyCategoryCreator dummyCategoryCreator = new DummyCategoryCreator();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryCreatorServiceDirect.class);

    @Override
    public void add(String categoryName) {
        ProductCategory category = productCategoryRepository.save(dummyCategoryCreator.create(categoryName));
        LOGGER.info("Product category " + category.getId() + " saved!");
    }
}
