package com.sofi.giphyconnector.api_rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Basic idempotent endpoint.
 * This can be used to validate that the service is correctly deployed on an application server and is connected to the right port.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

    private static final String message = "Service is running.";
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/status")
    public ResponseEntity<String> healthCheck() {

        LOGGER.info("Executing /Status endpoint");

        try {
            LOGGER.info("Execution successful");
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
