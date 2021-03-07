package com.sofi.giphyconnector.controllers;

import com.sofi.giphyconnector.DataTransferObjects.GenericResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Basic idempotent endpoint.
 * This can be used to validate that the service is correctly deployed on an application server and is connected to the right port.
 */
@RestController
public class HealthCheckController {

    private static final String message = "Service is running.";
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @GetMapping("/status")
    public ResponseEntity<GenericResponseDTO> healthCheck() {

        LOGGER.info("Executing /Status endpoint");

        try {
            GenericResponseDTO responseData = new GenericResponseDTO(message, HttpStatus.OK);
            LOGGER.info("Execution successful");
            return new ResponseEntity(responseData, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
