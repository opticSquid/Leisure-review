package com.cts.interim.beta.entities;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class ServiceProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String placeName;

	private String address;

	@Email
	@Transient
	private String email;
	
	private String ownerId;

	@Enumerated(EnumType.STRING)
	private PlaceType placeType;

	@Transient
	private JsonNode details;

	// Request will come using this constructor
	public ServiceProvider(String placeName, String address, String email, PlaceType placeType, JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.email = email;
		this.placeType = placeType;
		this.details = details;
	}

	// this constructor will be used to create empty serviceProvider
	public ServiceProvider(String placeName, String address, String ownerId, PlaceType placeType) {
		this.placeName = placeName;
		this.address = address;
		this.ownerId = ownerId;
		this.placeType = placeType;
	}

	// hotel, park, mall will be formed and linked using this constructor
	public ServiceProvider(String id, String placeName, String address, String ownerId) {
		this.id = id;
		this.placeName = placeName;
		this.address = address;
		this.ownerId = ownerId;
	}

}
