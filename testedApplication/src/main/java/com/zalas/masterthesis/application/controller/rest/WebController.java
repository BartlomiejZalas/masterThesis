package com.zalas.masterthesis.application.controller.rest;

import com.google.common.collect.Lists;
import com.zalas.masterthesis.application.controller.ProductCategoryCacheController;
import com.zalas.masterthesis.application.controller.ProductCategoryCreatorController;
import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import com.zalas.masterthesis.application.service.creator.DummyCategoryCreator;
import com.zalas.masterthesis.application.service.simulation.CpuExhaustor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductCategoryCacheController productCategoryCacheController;
    @Autowired
    private ProductCategoryCreatorController productCategoryCreatorController;
    @Autowired
    private CpuExhaustor cpuExhaustor;

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductCategory> findById(@PathVariable("id") int id) {
        ProductCategory productCategory = productCategoryCacheController.getService().findOne(id);
        HttpStatus status = productCategory == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(productCategory, status);
    }

    @PutMapping("/add")
    public ResponseEntity<ProductCategory> add(@RequestParam("categoryName") String categoryName) {
        ProductCategory createdCategory = productCategoryCreatorController.getService().add(categoryName);
        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ProductCategory> updateById(@PathVariable("id") int id, @RequestParam("newName") String newName) {
        ProductCategory productCategory = productCategoryCacheController.getService().update(id, newName);
        HttpStatus status = productCategory == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(productCategory, status);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeById(@PathVariable("id") int id) {
        productCategoryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity removeAll() {
        productCategoryRepository.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/addCategories")
    public ResponseEntity<String> fillDatabase(@RequestParam int size) {
        saveDummyCategories(size);
        return new ResponseEntity<>("Product categories saved", HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductCategory>> findAllProductCategories() {
        return new ResponseEntity<>(Lists.newArrayList(productCategoryRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/findPart")
    public ResponseEntity<List<ProductCategory>> findPartProductCategories(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(Lists.newArrayList(productCategoryRepository.findAll(new PageRequest(page, size)).getContent()), HttpStatus.OK);
    }

    @GetMapping("/task")
    public ResponseEntity cpuExhaustiveTask() {
        cpuExhaustor.executeCpuExhaustingTask(1);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void saveDummyCategories(int numberOfNewCategories) {
        DummyCategoryCreator dummyCategoryCreator = new DummyCategoryCreator();
        for (int i = 0; i < numberOfNewCategories; i++) {
            ProductCategory productCategory = dummyCategoryCreator.create("Category " + (i+1));
            productCategoryRepository.save(productCategory);
        }
    }
}

