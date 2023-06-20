package com.cts.interim_project.Reviews.and.Streaks.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RatingStats {
//	@Max(5)
//	@Min(1)
	private Integer rating;
	private Long count;
}
