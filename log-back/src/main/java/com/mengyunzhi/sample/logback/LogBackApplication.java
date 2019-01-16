package com.mengyunzhi.sample.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class LogBackApplication {
	private static final Logger logger = LoggerFactory.getLogger(LogBackApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogBackApplication.class, args);
	}

	@RequestMapping("send")
	public void send() {
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}
}