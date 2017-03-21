package com.zalas.masterthesis.application.redundantcomponents;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;

public class DirectDatabaseFindOneRC implements FindOneRedundantComponent {

    private ProductCategoryRepository productCategoryRepository;

    public DirectDatabaseFindOneRC(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory findOne(int id) {
        return productCategoryRepository.findOne(id);
    }
}
