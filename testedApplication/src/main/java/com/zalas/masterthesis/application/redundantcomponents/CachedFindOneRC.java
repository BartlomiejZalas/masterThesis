package com.zalas.masterthesis.application.redundantcomponents;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;

import java.util.HashMap;
import java.util.Map;

public class CachedFindOneRC implements FindOneRedundantComponent {

    private ProductCategoryRepository productCategoryRepository;
    private Map<Long, ProductCategory> cache = new HashMap<>();

    public CachedFindOneRC(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory findOne(long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            ProductCategory productCategory = productCategoryRepository.findOne(id);
            cache.put(id, productCategory);
            return productCategory;
        }
    }
}
