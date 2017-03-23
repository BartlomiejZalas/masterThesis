package com.zalas.masterthesis.application.service.creator;

import com.zalas.masterthesis.application.model.Product;
import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.model.ProductOpinion;

import java.util.Date;

import static com.google.common.collect.Sets.newHashSet;

public class DummyCategoryCreator {
    public ProductCategory create(String name) {
        ProductCategory category = new ProductCategory();
        category.setName(name);
        Product p1 = new Product();
        p1.setName("Product 1");
        p1.setProductCategory(category);
        p1.setProductOpinions(newHashSet(new ProductOpinion("Content 1", new Date(), p1)));
        Product p2 = new Product();
        p2.setName("Product 2");
        p2.setProductCategory(category);
        p2.setProductOpinions(newHashSet(new ProductOpinion("Content 2", new Date(), p2)));

        category.setProducts(newHashSet(p1, p2));
        return category;
    }
}
