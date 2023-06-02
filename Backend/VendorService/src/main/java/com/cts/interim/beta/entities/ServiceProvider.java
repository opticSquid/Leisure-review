package com.cts.interim.beta.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
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

	@Column(length = 500)
	private String address;

	private String ownerId;

	@Enumerated(EnumType.STRING)
	private PlaceType placeType;

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private JsonNode details;

	// Request will come using this constructor
	// used to create new vendors
	public ServiceProvider(String placeName, String address, PlaceType placeType, JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.placeType = placeType;
		this.details = details;
	}

	// hotel, park, mall will be formed and linked using this constructor
	// used for updating vendorDetails
	public ServiceProvider(String id, String placeName, PlaceType placeType, String address, String ownerId) {
		this.id = id;
		this.placeName = placeName;
		this.placeType = placeType;
		this.address = address;
		this.ownerId = ownerId;
	}

}
