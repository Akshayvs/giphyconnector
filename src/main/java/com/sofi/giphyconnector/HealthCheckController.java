package com.sofi.giphyconnector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static final String message = "Service is running";

    @GetMapping("/status")
    public String healthCheck () {
        return this.message;
    }


}
