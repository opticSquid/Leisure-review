package com.cts.interim_project.Reviews.and.Streaks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.entities.PlaceType;
import com.cts.interim_project.Reviews.and.Streaks.entities.ProviderReview;
import com.cts.interim_project.Reviews.and.Streaks.repository.ProviderReviewRepo;

@Service
public class ReviewService {
	@Autowired
	private ProviderReviewRepo reviewRepo;

	public String addReview(ProviderReview review) {
		ProviderReview savedReview = reviewRepo.save(review);
		return savedReview.getProviderId();
	}

	public List<ProviderReview> getAllServiceProviders() {
		return reviewRepo.findAll();
	}

	public ProviderReview getServiceProviderById(String id) {
		Optional<ProviderReview> serviceProvider = reviewRepo.findById(id);
		if (serviceProvider.isEmpty()) {
			throw new ProviderNotFoundException("service provider with given id is not found");
		} else {
			return serviceProvider.get();
		}
	}

	public List<ProviderReview> getServiceProvidersByType(PlaceType placeType) {
		List<ProviderReview> serviceProviders = reviewRepo.findByPlaceType(placeType);
		return serviceProviders;
	}

}
