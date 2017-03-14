package com.zalas.masterthesis.application.redundantcomponents;

import com.zalas.masterthesis.application.model.Customer;
import com.zalas.masterthesis.application.repo.CustomerRepository;

public class DirectDatabaseFindOneRC  implements FindOneRedundantComponent{

    private CustomerRepository customerRepository;

    public DirectDatabaseFindOneRC(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findOne(long id) {
        return customerRepository.findOne(id);
    }
}
