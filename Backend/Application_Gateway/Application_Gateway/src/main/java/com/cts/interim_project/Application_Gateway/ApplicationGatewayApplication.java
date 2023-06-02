package com.cts.interim_project.Application_Gateway;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApplicationGatewayApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "5000"));
		app.run(args);
	}

}
