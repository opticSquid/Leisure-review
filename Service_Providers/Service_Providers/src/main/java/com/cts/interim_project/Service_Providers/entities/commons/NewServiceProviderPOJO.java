package com.cts.interim_project.Service_Providers.entities.commons;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewServiceProviderPOJO {
	private String id;
	private String placeName;
	private String address;
	private String businessEmail;
	private String description;
	private PlaceType placeType;
	private JsonNode details;

	// For create operation
	// For update operation all Args constructor declared with lombok will be used
	public NewServiceProviderPOJO(String placeName, String address, String description, String businessEmail,
			JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.businessEmail = businessEmail;
		this.details = details;
	}
}
