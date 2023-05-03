package com.cts.interim_project.Service_Providers.entities;

import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
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
public class Mall extends ServiceProviders {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private Integer numberOfShops;
	private Double floorArea;
	private Integer numberOfParkingSpaces;
	private Integer numberofRestaurants;

	public Mall(String placeName, String address, String description, String ownerId, JsonNode details) {
		super(placeName, address, description, ownerId);
		this.numberOfShops = details.get("numberOfShops").asInt();
		this.floorArea = details.get("floorArea").asDouble();
		this.numberOfParkingSpaces = details.get("numberOfParkingSpaces").asInt();
		this.numberofRestaurants = details.get("numberofRestaurants").asInt();
	}

}
