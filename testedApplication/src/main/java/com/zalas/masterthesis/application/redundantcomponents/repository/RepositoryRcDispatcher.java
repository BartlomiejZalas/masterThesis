package com.zalas.masterthesis.application.redundantcomponents.repository;

import com.zalas.masterthesis.application.model.ProductCategory;
import com.zalas.masterthesis.application.repo.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class RepositoryRcDispatcher {

    private static final boolean USE_PAGGINATED_QUERY = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryRcDispatcher.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAll() {
        List<ProductCategory> categories = new ArrayList();
        long time = System.nanoTime();
        if (USE_PAGGINATED_QUERY) {
            for (int i = 0; i < 10; i++) {
                PageRequest pageRequest = new PageRequest(i, 100);
                categories.addAll(productCategoryRepository.findAll(pageRequest).getContent());
            }
        } else {
            categories = productCategoryRepository.findAll();
        }
        LOGGER.info("Execution time: " + String.valueOf(System.nanoTime() - time));
        return categories;
    }

}
