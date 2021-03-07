package com.sofi.giphyconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class GiphyconnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiphyconnectorApplication.class, args);
    }
}
