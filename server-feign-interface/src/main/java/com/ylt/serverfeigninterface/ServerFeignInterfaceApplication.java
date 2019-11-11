package com.ylt.serverfeigninterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ylt"}) //开启feign负载均衡
@ComponentScan("com.ylt")
public class ServerFeignInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerFeignInterfaceApplication.class, args);
	}

}
