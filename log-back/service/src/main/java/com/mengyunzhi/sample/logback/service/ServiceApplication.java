package com.mengyunzhi.sample.logback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@SpringBootApplication
@RestController
public class ServiceApplication {
    private final static Logger logger = LoggerFactory.getLogger(ServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @RequestMapping("log")
    public void log(HttpServletRequest httpServletRequest) throws IOException {
        logger.info(httpServletRequest.toString());
        BufferedReader bufferedReader = httpServletRequest.getReader();
        String str, wholeStr = "";
        while((str = bufferedReader.readLine()) != null) {
            wholeStr += str;
        }
        logger.info(wholeStr);
    }
}

