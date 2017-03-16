package com.zalas.masterthesis.application.redundantcomponents;

import com.zalas.masterthesis.application.model.Customer;
import com.zalas.masterthesis.application.repo.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

public class CachedFindOneRC implements FindOneRedundantComponent {

    private CustomerRepository customerRepository;
    private Map<Long, Customer> cache = new HashMap<>();

    public CachedFindOneRC(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findOne(long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        } else {
            Customer customer = customerRepository.findOne(id);
            cache.put(id, customer);
            return customer;
        }
    }
}
