package com.cts.interim_project.Reviews.and.Streaks.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Comments {
	@Id
	private String commentId;
	private String userId;
	private Double rating;
}
