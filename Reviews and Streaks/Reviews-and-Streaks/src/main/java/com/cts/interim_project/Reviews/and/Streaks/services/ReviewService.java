package com.cts.interim_project.Reviews.and.Streaks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.entities.Review;
import com.cts.interim_project.Reviews.and.Streaks.repository.ReviewRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {
	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private RestTemplate restTemplate;

	public String addReview(Review review) {
		ResponseEntity<Boolean> isServiceProviderValid = restTemplate
				.getForEntity("http://PROVIDERS/providers/check/" + review.getServiceProviderId(), Boolean.class);
		if (isServiceProviderValid.getBody()) {
			Review savedReview = reviewRepo.save(review);
			return savedReview.getReviewId();
		} else {
			throw new ProviderNotFoundException("No service provider with given id is found");
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

	public List<Review> getAllReviewsOfUser(String userId) {
		return reviewRepo.findAllReviewsOfUser(userId);
	}

	public String editReview(Review review) {
		Review existingReview = getReviewById(review.getReviewId());
		if (existingReview != null) {
			reviewRepo.save(review);
			return review.getReviewId();
		} else {
			throw new ProviderNotFoundException("service provider with gived id is not found");
		}
	}

	public void deleteReview(String reviewId) {
		reviewRepo.deleteById(reviewId);
	}
}
