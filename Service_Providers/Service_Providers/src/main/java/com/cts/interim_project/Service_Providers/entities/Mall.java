package com.cts.interim_project.Service_Providers.entities;

import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;

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

	public Mall(String placeName, String address, String description, String ownerId, Integer numberOfShops,
			Double floorArea, Integer numberOfParkingSpaces, Integer numberofRestaurants) {
		super(placeName, address, description, ownerId);
		this.numberOfShops = numberOfShops;
		this.floorArea = floorArea;
		this.numberOfParkingSpaces = numberOfParkingSpaces;
		this.numberofRestaurants = numberofRestaurants;
	}

}
