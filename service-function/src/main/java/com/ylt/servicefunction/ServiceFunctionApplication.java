package com.ylt.servicefunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableEurekaClient
public class ServiceFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFunctionApplication.class, args);
	}

}
