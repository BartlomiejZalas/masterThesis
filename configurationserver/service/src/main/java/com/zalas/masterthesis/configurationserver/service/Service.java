package com.zalas.masterthesis.configurationserver.service;

import com.zalas.masterthesis.configurationserver.api.model.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service {

    @Autowired
    private ConfigurationService configurationService;

    private static final Logger LOG = LoggerFactory.getLogger(Service.class);

    @GetMapping("/get")
    public ResponseEntity<String> getConfiguration(@RequestParam("key") String configurationKey) {
        try {
            return new ResponseEntity<>(configurationService.get(configurationKey), HttpStatus.OK);
        } catch (ConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApplicationConfiguration> getAllConfiguration() {
        return new ResponseEntity<>(configurationService.getConfiguration(), HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<String> changeConfiguration(@RequestParam("key") String configurationKey, @RequestParam("value") String configurationValue) {
        try {
            configurationService.change(configurationKey, configurationValue);
            LOG.info("Configuration of " + configurationKey + " changed to value: " + configurationValue);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

