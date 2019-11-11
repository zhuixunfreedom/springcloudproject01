package com.ylt.servicelogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceLoginApplication.class, args);
	}

}
