package com.zalas.masterthesis.application.service.creator;

import com.zalas.masterthesis.application.model.Product;
import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.model.ProductOpinion;

import java.util.Date;
import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;

public class DummyCategoryCreator {
    public ProductCategory create(String name) {
        ProductCategory category = new ProductCategory();
        category.setName(name);

        HashSet<Product> products = newHashSet();
        for (int p = 1; p <= (int) (Math.random() * 4) + 1; p++) {
            Product product = new Product();
            product.setName("Product " + p);
            product.setProductCategory(category);

            HashSet<ProductOpinion> opinions = newHashSet();
            for (int o = 1; o <= (int) (Math.random() * 4) + 1; o++) {
                ProductOpinion opinion = new ProductOpinion("Content " + o, new Date(), product);
                opinions.add(opinion);
            }
            product.setProductOpinions(opinions);
            products.add(product);
        }
        category.setProducts(products);
        return category;
    }
}
