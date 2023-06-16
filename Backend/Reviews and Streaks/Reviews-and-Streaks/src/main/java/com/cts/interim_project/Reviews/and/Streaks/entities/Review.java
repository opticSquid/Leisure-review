package com.cts.interim_project.Reviews.and.Streaks.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String reviewId;
	@Nonnull
	private String serviceProviderId;
	@Nonnull
	private String userId;
	@Column(length = 500)
	private String review;
	@Max(value = 5)
	private Integer rating;

	public Review(String serviceProviderId, String userId, String review, Integer rating) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.userId = userId;
		this.review = review;
		this.rating = rating;
	}
}
