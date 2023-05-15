package com.cts.interim.beta.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Mall extends ServiceProvider {
	private Integer numberOfShops;
	private Double floorArea;
	private Integer numberOfParkingSpaces;
	private Integer numberofRestaurants;

	public Mall(ServiceProvider vendor) {
		super(vendor.getId(), vendor.getPlaceName(), PlaceType.mall, vendor.getAddress(), vendor.getOwnerId());
		this.numberOfShops = vendor.getDetails().get("numberOfShops").asInt();
		this.floorArea = vendor.getDetails().get("floorArea").asDouble();
		this.numberOfParkingSpaces = vendor.getDetails().get("numberOfParkingSpaces").asInt();
		this.numberofRestaurants = vendor.getDetails().get("numberofRestaurants").asInt();
	}
}
