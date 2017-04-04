package com.zalas.masterthesis.configurationserver.service;

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

    @GetMapping("/get")
    public ResponseEntity<String> getConfiguration(@RequestParam("key") String configurationKey) {
        try {
            return new ResponseEntity<>(configurationService.get(configurationKey), HttpStatus.OK);
        } catch (ConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<String> changeConfiguration(@RequestParam("key") String configurationKey, @RequestParam("value") String configurationValue) {
        try {
            configurationService.change(configurationKey, configurationValue);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

