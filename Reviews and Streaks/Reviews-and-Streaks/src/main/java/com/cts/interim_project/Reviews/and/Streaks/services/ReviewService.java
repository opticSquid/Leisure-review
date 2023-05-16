package com.cts.interim_project.Reviews.and.Streaks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.config.Role;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateRequest;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateResponse;
import com.cts.interim_project.Reviews.and.Streaks.entities.Review;
import com.cts.interim_project.Reviews.and.Streaks.entities.ReviewPOJO;
import com.cts.interim_project.Reviews.and.Streaks.exception.UserNotValidException;
import com.cts.interim_project.Reviews.and.Streaks.repository.ReviewRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {
	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private RestTemplate restTemplate;

	private ResponseEntity<ValidateResponse> validateUser(String token) {
		try {
			return restTemplate.postForEntity("http://USERS/users/auth/validate",
					new ValidateRequest(token.substring(7)), ValidateResponse.class);
		} catch (HttpClientErrorException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ValidateResponse(null, null, "jwt error"));
		}
	}

	Boolean isVendorValid(String id) {
		return restTemplate.getForObject("http://VENDORS/vendors/check/" + id, Boolean.class);
	}

	public String addReview(String token, ReviewPOJO review) {
		ResponseEntity<ValidateResponse> user = validateUser(token);
		Boolean isServiceProviderValid = isVendorValid(review.getServiceProviderId());
		if (user.getBody().getUserId() != null && user.getBody().getRole() == Role.USER && isServiceProviderValid) {
			Review savedReview = reviewRepo.save(new Review(review.getServiceProviderId(), user.getBody().getUserId(),
					review.getReview(), review.getRating()));
			return savedReview.getReviewId();
		} else if (user.getBody().getUserId() == null || user.getBody().getRole() != Role.USER) {
			throw new UserNotValidException("user is not valid", new Throwable("given user is not found in database"));
		} else {
			throw new ProviderNotFoundException("No vendor with given id is found");
		}
	}

	private Review getReviewById(String reviewId) throws ProviderNotFoundException {
		Optional<Review> review = reviewRepo.findById(reviewId);
		if (review.isPresent()) {
			return review.get();
		} else {
			throw new ProviderNotFoundException("service provider with gived id is not found");
		}
	}

	public List<Review> getAllReviewsOfServiceProvider(String providerId) {
		return reviewRepo.findAllReviewsOfServiceProvider(providerId);
	}

	public List<Review> getAllReviewsOfUser(String token) {
		ResponseEntity<ValidateResponse> user = validateUser(token);
		if (user.getBody().getUserId() != null && user.getBody().getRole() == Role.USER) {
			return reviewRepo.findAllReviewsOfUser(user.getBody().getUserId());
		} else {
			throw new UserNotValidException("user is not valid", new Throwable("given user is not found in database"));
		}
	}

	public String editReview(String token, Review review) {
		ResponseEntity<ValidateResponse> user = validateUser(token);
		Boolean isServiceProviderValid = isVendorValid(review.getServiceProviderId());
		Review existingReview = getReviewById(review.getReviewId());
		if (user.getBody().getUserId().equals(review.getUserId()) && existingReview != null && isServiceProviderValid) {
			reviewRepo.save(review);
			return review.getReviewId();
		} else {
			throw new ProviderNotFoundException("service provider with gived id is not found");
		}
	}

	public void deleteReview(String token, String reviewId) {
		ResponseEntity<ValidateResponse> user = validateUser(token);
		Review existingReview = getReviewById(reviewId);
		if (user.getBody().getUserId().equals(existingReview.getUserId())) {
			reviewRepo.deleteById(reviewId);
		}
	}
}
