package com.cts.interim_project.Service_Providers;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceProvidersApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServiceProvidersApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "0"));
		app.run(args);
	}

}
