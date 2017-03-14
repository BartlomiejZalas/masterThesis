package com.zalas.masterthesis.application.repo;

import java.util.List;

import com.zalas.masterthesis.application.model.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long>{
	List<Customer> findByLastName(String lastName);
}
