package com.zalas.masterthesis.application;

import com.zalas.masterthesis.application.redundantcomponents.repository.RepositoryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RepositoryConfiguration.class)
public class SpringJpaPostgreSqlApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringJpaPostgreSqlApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaPostgreSqlApplication.class, args);
    }
}
