package com.zalas.masterthesis.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringJpaPostgreSqlApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringJpaPostgreSqlApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaPostgreSqlApplication.class, args);
	}
}
