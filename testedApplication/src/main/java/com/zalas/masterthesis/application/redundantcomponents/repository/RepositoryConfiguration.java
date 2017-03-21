package com.zalas.masterthesis.application.redundantcomponents.repository;

import org.springframework.context.annotation.Bean;

public class RepositoryConfiguration {
    @Bean
    public RepositoryRcDispatcher repositoryRcDispatcher() {
        return new RepositoryRcDispatcher();
    }

}
