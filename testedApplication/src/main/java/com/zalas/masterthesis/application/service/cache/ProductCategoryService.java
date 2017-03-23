package com.zalas.masterthesis.application.service.cache;

import com.zalas.masterthesis.application.model.ProductCategory;

public interface ProductCategoryService {
    ProductCategory findOne(int id);

    ProductCategory update(int id, String newName);
}
