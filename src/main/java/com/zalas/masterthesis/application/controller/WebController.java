package com.zalas.masterthesis.application.controller;

import com.google.common.collect.Lists;
import com.zalas.masterthesis.application.model.Customer;
import com.zalas.masterthesis.application.redundantcomponents.FindOneRedundantComponent;
import com.zalas.masterthesis.application.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebController {

	@Autowired
	CustomerRepository repository;
	
	@RequestMapping("/save")
	public ResponseEntity<String> process(){
		createDummyData();
		return new ResponseEntity<>("Done", HttpStatus.CREATED);
	}

	@RequestMapping("/findAll")
	public ResponseEntity<List<Customer>> findAll(){
		return new ResponseEntity<>(Lists.newArrayList(repository.findAll()), HttpStatus.OK);
	}


	@RequestMapping("/findById")
	public ResponseEntity<Customer> findById(@RequestParam("id") long id){
		FindOneRedundantComponentResolver resolver = new FindOneRedundantComponentResolver(repository);
        return new ResponseEntity<>(resolver.resolve().findOne(id), HttpStatus.OK);
	}

	private void createDummyData() {
		repository.save(new Customer("Jack", "Smith"));
		repository.save(new Customer("Adam", "Johnson"));
		repository.save(new Customer("Kim", "Smith"));
		repository.save(new Customer("David", "Williams"));
		repository.save(new Customer("Peter", "Davis"));
	}
}

