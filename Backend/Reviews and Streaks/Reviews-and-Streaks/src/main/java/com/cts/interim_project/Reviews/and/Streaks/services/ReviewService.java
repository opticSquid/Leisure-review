package com.cts.interim_project.Reviews.and.Streaks.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.Exceptions.UserNotValidException;
import com.cts.interim_project.Reviews.and.Streaks.config.Role;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateRequest;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateResponse;
import com.cts.interim_project.Reviews.and.Streaks.entities.RatingStats;
import com.cts.interim_project.Reviews.and.Streaks.entities.Review;
import com.cts.interim_project.Reviews.and.Streaks.entities.ReviewPOJO;
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

	private Boolean isVendorValid(String id) {
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

	private Review getReviewById(String reviewId) {
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

	public Double getAverageRating(String providerId) {
		Boolean isVendorValid = isVendorValid(providerId);
		if (isVendorValid) {
			Optional<Double> data = reviewRepo.getAverageRatingOfServiceProvider(providerId);
			if (data.isPresent()) {
				return data.get();
			} else {
				throw new ProviderNotFoundException("no rating exist for the given service provider");
			}
		} else {
			throw new ProviderNotFoundException("service provider with gived id is not found");
		}

	}
	private List<RatingStats> addMissingStats(List<RatingStats> ratingStatsList){
		List<RatingStats> updatedList = new ArrayList<>();
        Map<Integer, RatingStats> ratingMap = new HashMap<>();

        for (RatingStats ratingStats : ratingStatsList) {
            ratingMap.put(ratingStats.getRating(), ratingStats);
        }

        for (int i = 1; i <= 5; i++) {
            RatingStats ratingStats = ratingMap.get(i);
            if (ratingStats != null) {
                updatedList.add(ratingStats);
            } else {
                updatedList.add(new RatingStats(i, 0L));
            }
        }
        updatedList.sort(Comparator.comparingInt(RatingStats::getRating));
        return updatedList;
    }
	
	public List<RatingStats> getRatingStatsOfServiceProvider(String providerId) {
		Boolean isVendorValid = isVendorValid(providerId);
		if (isVendorValid) {
		List<Object> stats = reviewRepo.getRatingStatsOfServiceProvider(providerId);
		List<RatingStats> st = new ArrayList<>();
		for(Object o: stats) {
			Object[] objectArray = (Object[]) o;
			RatingStats r = new RatingStats((Integer)objectArray[0], (Long)objectArray[1]);
			st.add(r);
		}
		//TODO
		// need to find a more optimized way of handling this problem
		// this code finds missing rating values and adds (rating,0) to them
		// like if a stats contain ((2,10),(4,6)) it will convert to
		// ((1,0),(2,10),(3,0),(4,6),(5,0))
		st = addMissingStats(st);
		return st;
		} else {
			throw new ProviderNotFoundException("service provider with gived id is not found");
		}
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
