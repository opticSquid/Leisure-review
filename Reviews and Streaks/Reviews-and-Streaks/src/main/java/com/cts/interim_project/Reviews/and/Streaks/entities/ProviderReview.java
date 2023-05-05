package com.cts.interim_project.Reviews.and.Streaks.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderReview {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String providerId;
	private PlaceType placeType;
	private Double averageReview;
	@OneToMany
	private List<Comments> comments;
}
