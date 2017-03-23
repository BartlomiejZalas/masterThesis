package com.zalas.masterthesis.application.controller.rest;

import com.google.common.collect.Lists;
import com.zalas.masterthesis.application.controller.ProductCategoryCacheController;
import com.zalas.masterthesis.application.controller.ProductCategoryCreatorController;
import com.zalas.masterthesis.application.model.Product;
import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.model.ProductOpinion;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;

@RestController
public class WebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductCategoryCacheController productCategoryCacheController;
    @Autowired
    private ProductCategoryCreatorController productCategoryCreatorController;

    @PutMapping("/fillDatabase")
    public ResponseEntity<String> fillDatabase() {
        saveDummyCategories();
        return new ResponseEntity<>("Product Categoreis saved", HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductCategory>> findAllProductCategories() {
        return new ResponseEntity<>(Lists.newArrayList(productCategoryRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/findPart")
    public ResponseEntity<List<ProductCategory>> findPartProductCategories(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(Lists.newArrayList(productCategoryRepository.findAll(new PageRequest(page, size)).getContent()), HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<ProductCategory> findById(@RequestParam("id") int id) {
        ResponseEntity<ProductCategory> responseEntity = new ResponseEntity<>(productCategoryCacheController.getService().findOne(id), HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/updateById")
    public ResponseEntity<ProductCategory> updateById(@RequestParam("id") int id, @RequestParam("newName") String newName) {
        return new ResponseEntity<>(productCategoryCacheController.getService().update(id, newName), HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<String> add(@RequestParam("categoryName") String categoryName) {
        productCategoryCreatorController.getService().add(categoryName);
        return new ResponseEntity<String>("Product category added!", HttpStatus.OK);
    }


    private void saveDummyCategories() {
        for (int i = 0; i < 5000; i++) {
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

