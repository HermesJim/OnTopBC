package com.getontop.businesscase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.getontop.businesscase.application.port.out.proxy"})
@SpringBootApplication
public class BusinessCaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessCaseApplication.class, args);
	}

}
