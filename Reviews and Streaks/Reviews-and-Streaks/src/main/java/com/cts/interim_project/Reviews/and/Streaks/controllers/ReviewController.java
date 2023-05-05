package com.cts.interim_project.Reviews.and.Streaks.controllers;

import java.net.URI;
import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim_project.Reviews.and.Streaks.entities.PlaceType;
import com.cts.interim_project.Reviews.and.Streaks.entities.ProviderReview;
import com.cts.interim_project.Reviews.and.Streaks.services.ReviewService;

@RestController
@RequestMapping("/review-streaks")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/new")
	public ResponseEntity<String> addReview(ProviderReview review) {
		String id = reviewService.addReview(review);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/review-streaks/{id}")
				.buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/get/{type}")
	public ResponseEntity<List<ProviderReview>> getProvidersByType(@PathVariable("type") String type) {
		if (type.equals("hotel")) {
			return ResponseEntity.status(HttpStatus.SC_OK)
					.body(reviewService.getServiceProvidersByType(PlaceType.Hotel));
		} else if (type.equals("park")) {
			return ResponseEntity.status(HttpStatus.SC_OK)
					.body(reviewService.getServiceProvidersByType(PlaceType.Park));
		} else {
			return ResponseEntity.status(HttpStatus.SC_OK)
					.body(reviewService.getServiceProvidersByType(PlaceType.Mall));
		}
	}
}