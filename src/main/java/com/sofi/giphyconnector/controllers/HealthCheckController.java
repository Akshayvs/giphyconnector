package com.sofi.giphyconnector.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    /**
     *
     * Basic idempotent endpoint.
     * This can be used to validate that the service is correctly deployed on an application server and is connected to the right port.
     *
     */

    private static final String message = "Service is running";

    @GetMapping("/status")
    public String healthCheck () {
        return this.message;
    }


}
