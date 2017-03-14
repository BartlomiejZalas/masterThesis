package com.zalas.masterthesis.application.controller;

import com.zalas.masterthesis.application.redundantcomponents.CachedFindOneRC;
import com.zalas.masterthesis.application.redundantcomponents.DirectDatabaseFindOneRC;
import com.zalas.masterthesis.application.redundantcomponents.FindOneRedundantComponent;
import com.zalas.masterthesis.application.repo.CustomerRepository;

public class FindOneRedundantComponentResolver {

    public final static int COMPONENT_CHOICE = 0;

    private CustomerRepository repository;

    public FindOneRedundantComponentResolver(CustomerRepository repository) {
        this.repository = repository;
    }


    public FindOneRedundantComponent resolve() {
        if (COMPONENT_CHOICE == 0) {
            return new DirectDatabaseFindOneRC(repository);
        } else {
            return new CachedFindOneRC(repository);
        }

    }
}
