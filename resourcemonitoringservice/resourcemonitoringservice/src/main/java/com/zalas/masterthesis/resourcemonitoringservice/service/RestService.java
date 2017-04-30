package com.zalas.masterthesis.resourcemonitoringservice.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @Autowired
    private MonitoringService monitoringService;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RestService.class);

    @GetMapping("/start")
    public ResponseEntity<String> start() {
        try {
            monitoringService.start();
            return new ResponseEntity<>("Resource monitoring started", HttpStatus.OK);
        } catch (MonitoringServiceUsageException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stop() {
        try {
            monitoringService.stop();
            return new ResponseEntity<>("Resource monitoring stopped", HttpStatus.OK);
        } catch (MonitoringServiceUsageException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

