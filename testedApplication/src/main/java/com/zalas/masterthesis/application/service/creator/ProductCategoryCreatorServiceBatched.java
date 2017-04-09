package com.zalas.masterthesis.application.service.creator;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductCategoryCreatorServiceBatched implements ProductCreatorService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private static final int BATCH_LIMIT = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryCreatorServiceBatched.class);

    private DummyCategoryCreator dummyCategoryCreator = new DummyCategoryCreator();
    private List<ProductCategory> batchedCategories = new ArrayList<>();

    @Override
    public ProductCategory add(String categoryName) {
        ProductCategory newCategory = dummyCategoryCreator.create(categoryName);
        batchedCategories.add(newCategory);

        if (batchedCategories.size() == BATCH_LIMIT) {
            productCategoryRepository.save(batchedCategories);
            LOGGER.info("Batch saved, ids: " + Arrays.toString(batchedCategories.stream().map(ProductCategory::getId).toArray()));
            batchedCategories.clear();
        }
        LOGGER.info("Batch size: " + batchedCategories.size());
        return newCategory;
    }
}
