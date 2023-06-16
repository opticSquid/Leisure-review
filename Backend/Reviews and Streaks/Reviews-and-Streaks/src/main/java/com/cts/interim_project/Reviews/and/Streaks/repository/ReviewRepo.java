package com.cts.interim_project.Reviews.and.Streaks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.interim_project.Reviews.and.Streaks.entities.Review;

public interface ReviewRepo extends JpaRepository<Review, String> {
//	@Query(value = "SELECT AVG(average_review) from review where provider_id=?1", nativeQuery = true)
//	Double computeAverageRating(String id);
	@Query(value = "SELECT * FROM review where service_provider_id=?1", nativeQuery = true)
	List<Review> findAllReviewsOfServiceProvider(String providerId);

	@Query(value = "SELECT * FROM review where user_id=?1", nativeQuery = true)
	List<Review> findAllReviewsOfUser(String userId);

	@Query(value = "select avg(rating) from review where service_provider_id=?1", nativeQuery = true)
	Optional<Double> getAverageRatingOfServiceProvider(String providerId);

	@Query(value = "SELECT rating as rating, COUNT(*) AS count FROM review WHERE service_provider_id = ?1 GROUP BY rating", nativeQuery = true)
	List<Object> getRatingStatsOfServiceProvider(String providerId);
}
