package com.sofi.giphyconnector.Utility;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class RestTemplate {
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 2500;

    public static org.springframework.web.client.RestTemplate RestTemplateWithTimeout() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECTION_TIMEOUT_MILLISECONDS);
        factory.setReadTimeout(CONNECTION_TIMEOUT_MILLISECONDS);

        return new org.springframework.web.client.RestTemplate(factory);
    }
}
