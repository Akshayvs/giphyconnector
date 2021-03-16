package com.sofi.giphyconnector;

import com.sofi.giphyconnector.Utility.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GiphyconnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiphyconnectorApplication.class, args);
    }
}
