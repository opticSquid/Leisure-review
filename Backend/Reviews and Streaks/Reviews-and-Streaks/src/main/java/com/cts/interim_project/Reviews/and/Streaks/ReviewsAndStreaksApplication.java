package com.cts.interim_project.Reviews.and.Streaks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Reviews Service API", version = "1.0", description = "This service works with the reviews given by different users on different vendor services"))
public class ReviewsAndStreaksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsAndStreaksApplication.class, args);
	}

}
