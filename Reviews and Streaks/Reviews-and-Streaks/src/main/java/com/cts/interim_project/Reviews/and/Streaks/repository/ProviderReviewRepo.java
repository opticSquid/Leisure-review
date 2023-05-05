package com.cts.interim_project.Reviews.and.Streaks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.interim_project.Reviews.and.Streaks.entities.PlaceType;
import com.cts.interim_project.Reviews.and.Streaks.entities.ProviderReview;

public interface ProviderReviewRepo extends JpaRepository<ProviderReview, String> {
	@Query(value = "SELECT AVG(average_review) from provider_review where provider_id=?1", nativeQuery = true)
	Double computeAverageRating(String id);

	List<ProviderReview> findByPlaceType(PlaceType placeType);
}
