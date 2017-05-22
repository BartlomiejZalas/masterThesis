package com.zalas.masterthesis.application.service.cache;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import com.zalas.masterthesis.application.service.simulation.CpuExhaustor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceNoCache implements ProductCategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceNoCache.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private CpuExhaustor cpuExhaustor;

    public ProductCategory findOne(int id) {
        LOGGER.info("findOne() no cache used");
        return productCategoryRepository.findOne(id);
    }

    @Override
    public ProductCategory update(int id, String newName) {
        ProductCategory productCategory = productCategoryRepository.findOne(id);
        if (productCategory == null) {
            return null;
        }
        productCategory.setName(newName);
        ProductCategory savedCategory = productCategoryRepository.save(productCategory);

        LOGGER.info("productCategory updated");
        return savedCategory;
    }

    @Override
    public void waitTask(int sleepTime) throws InterruptedException {
        Thread.sleep(sleepTime);
    }

    @Override
    public void executeCpuExhaustingTask() {
        cpuExhaustor.executeCpuExhaustingTask();
    }


}
