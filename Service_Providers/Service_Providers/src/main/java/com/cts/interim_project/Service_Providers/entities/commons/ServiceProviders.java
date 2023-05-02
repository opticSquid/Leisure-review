package com.cts.interim_project.Service_Providers.entities.commons;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class ServiceProviders {
	protected String placeName;
	protected String address;
	protected String description;
	protected String ownerId;
	private PlaceType placeType;
	private JsonNode details;

	public ServiceProviders(String placeName, String address, String description, String ownerId) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
	}

}
