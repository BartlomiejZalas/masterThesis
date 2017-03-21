package com.zalas.masterthesis.application.controller;

import com.google.common.collect.Lists;
import com.zalas.masterthesis.application.model.Product;
import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.model.ProductOpinion;
import com.zalas.masterthesis.application.redundantcomponents.repository.RepositoryRcDispatcher;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;

@RestController
public class WebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private RepositoryRcDispatcher repositoryRcDispatcher;

    @RequestMapping("/save")
    public ResponseEntity<String> saveProductCategories() {
        saveDummyCategories();
        return new ResponseEntity<>("Product Categoreis saved", HttpStatus.CREATED);
    }

    @RequestMapping("/findAll")
    public ResponseEntity<List<ProductCategory>> findAllProductCategories() {
//        LOGGER.info("MESAGE: : " + repositoryRcDispatcher.findAll());
        return new ResponseEntity<>(Lists.newArrayList(repositoryRcDispatcher.findAll()), HttpStatus.OK);
    }

    @RequestMapping("/findById")
    public ResponseEntity<ProductCategory> findById(@RequestParam("id") long id) {
        FindOneRedundantComponentResolver resolver = new FindOneRedundantComponentResolver(productCategoryRepository);
        return new ResponseEntity<>(resolver.resolve().findOne(id), HttpStatus.OK);
    }


    private void saveDummyCategories() {
        for (int i = 0; i < 500; i++) {
            ProductCategory categoryA = new ProductCategory();
            categoryA.setName("Category A");
            Product p1 = new Product();
            p1.setName("Product 1");
            p1.setProductCategory(categoryA);
            p1.setProductOpinions(newHashSet(new ProductOpinion("Content 1", new Date(), p1)));
            Product p2 = new Product();
            p2.setName("Product 2");
            p2.setProductCategory(categoryA);
            p2.setProductOpinions(newHashSet(new ProductOpinion("Content 2", new Date(), p2)));

            categoryA.setProducts(newHashSet(p1, p2));

            ProductCategory categoryB = new ProductCategory();
            categoryB.setName("Category B");
            Product p3 = new Product();
            p3.setName("Product 1");
            p3.setProductCategory(categoryB);
            p3.setProductOpinions(newHashSet(new ProductOpinion("Content 3a", new Date(), p3), new ProductOpinion("Content 3b", new Date(), p3)));

            categoryB.setProducts(newHashSet(p3));

            productCategoryRepository.save(newHashSet(categoryA, categoryB));
        }
    }
}

