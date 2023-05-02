package com.cts.interim_project.Reviews.and.Streaks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReviewsAndStreaksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsAndStreaksApplication.class, args);
	}

}
