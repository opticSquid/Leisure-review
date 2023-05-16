package com.cts.interim_project.Reviews.and.Streaks.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPOJO {
	@Nonnull
	private String serviceProviderId;
	@Column(length = 500)
	private String review;
	private Integer rating;
}
