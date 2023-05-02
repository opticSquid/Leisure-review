package com.cts.interim_project.Service_Registry;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ServiceRegistryApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8761"));
		        app.run(args);
	}

}
