package com.cts.interim_project.Reviews.and.Streaks.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RatingStats {
	private Integer rating;
	private Long count;

//	public void setRating(Integer rating, Long count) {
//		this.rating.put(rating, count);
//	}

}
