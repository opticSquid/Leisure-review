package com.cts.interim.beta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Vendor Service API", version = "1.0", description = "This service works with the vendors and their associated details here users can register as service owners and add their business details"))
public class VendorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendorServiceApplication.class, args);
	}

}
