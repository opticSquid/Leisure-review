package com.cts.interim_project.Reviews.and.Streaks.controllers;

import java.net.URI;
import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim_project.Reviews.and.Streaks.entities.Review;
import com.cts.interim_project.Reviews.and.Streaks.repository.ReviewRepo;
import com.cts.interim_project.Reviews.and.Streaks.services.ReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@PostMapping("/new")
	public ResponseEntity<String> addReview(@RequestBody Review review) {
		log.error("Coming review: {}", review);
		String id = reviewService.addReview(review);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/reviews/{id}").buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/provider/get/{providerId}")
	public ResponseEntity<List<Review>> getAllReviewsOfServiceProvider(@PathVariable("providerId") String providerId) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(reviewService.getAllReviewsOfServiceProvider(providerId));
	}

	@GetMapping("/user/get/{userId}")
	public ResponseEntity<List<Review>> getAllReviewsOfUser(@PathVariable("userId") String userId) {
		return ResponseEntity.status(HttpStatus.SC_OK).body(reviewService.getAllReviewsOfUser(userId));
	}

	@PutMapping("/edit")
	public ResponseEntity<Review> editReview(@RequestBody Review review) {
		String id = reviewService.editReview(review);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/reviews/{id}").buildAndExpand(id)
				.toUri();
		return ResponseEntity.accepted().header("location", location.toString()).build();
	}

	@DeleteMapping("/delete/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable("reviewId") String reviewId) {
		reviewService.deleteReview(reviewId);
		return ResponseEntity.ok().build();
	}
}