package com.cts.interim_project.Service_Providers.entities;

import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Mall {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	protected String placeName;
	protected String address;
	protected String description;
	protected String ownerId;
	private PlaceType placeType;
	private Integer numberOfShops;
	private Double floorArea;
	private Integer numberOfParkingSpaces;
	private Integer numberofRestaurants;

	public Mall() {
		super();
	}

	public Mall(String placeName, String address, String description, String ownerId, JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Mall;
		this.numberOfShops = details.get("numberOfShops").asInt();
		this.floorArea = details.get("floorArea").asDouble();
		this.numberOfParkingSpaces = details.get("numberOfParkingSpaces").asInt();
		this.numberofRestaurants = details.get("numberofRestaurants").asInt();
	}

	public Mall(String placeName, String address, String description, String ownerId, Integer numberOfShops,
			Double floorArea, Integer numberOfParkingSpaces, Integer numberofRestaurants) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Mall;
		this.numberOfShops = numberOfShops;
		this.floorArea = floorArea;
		this.numberOfParkingSpaces = numberOfParkingSpaces;
		this.numberofRestaurants = numberofRestaurants;
	}
}
