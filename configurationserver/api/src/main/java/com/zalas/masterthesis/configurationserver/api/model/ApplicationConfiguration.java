package com.zalas.masterthesis.configurationserver.api.model;

import static com.zalas.masterthesis.configurationserver.api.constants.ConfigurationConstants.Value;

public class ApplicationConfiguration {

    private final Value batch;
    private final Value cache;
    private final Value threads;

    public ApplicationConfiguration(Value batch, Value cache, Value threads) {
        this.batch = batch;
        this.cache = cache;
        this.threads = threads;
    }

    public Value getBatch() {
        return batch;
    }

    public Value getCache() {
        return cache;
    }

    public Value getThreads() {
        return threads;
    }
}
