package com.zalas.masterthesis.application.service.productcategory;

import com.zalas.masterthesis.application.model.ProductCategory;
import org.springframework.http.ResponseEntity;

public interface ProductCategoryService {
    ProductCategory findOne(int id);

    ProductCategory update(int id, String newName);
}
