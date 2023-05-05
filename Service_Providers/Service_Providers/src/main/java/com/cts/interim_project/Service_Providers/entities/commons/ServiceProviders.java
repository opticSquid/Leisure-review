package com.cts.interim_project.Service_Providers.entities.commons;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceProviders {
	private String id;
	protected String placeName;
	protected String address;
	protected String description;
	protected String ownerId;
	private PlaceType placeType;
	private JsonNode details;

	// For create operation
	public ServiceProviders(String placeName, String address, String description, String ownerId) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
	}

	// For update operation
	public ServiceProviders(String id, String placeName, String address, String description, String ownerId) {
		this.id = id;
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
	}

}
