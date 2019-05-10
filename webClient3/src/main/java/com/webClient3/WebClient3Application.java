package com.webClient3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebClient3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebClient3Application.class, args);
	}

	@Bean
	public RestTemplate myRestTemplate() {
		return new RestTemplate();
	}

}
