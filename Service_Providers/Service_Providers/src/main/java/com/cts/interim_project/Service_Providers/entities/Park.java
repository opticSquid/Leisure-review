package com.cts.interim_project.Service_Providers.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Park {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	protected String placeName;
	protected String address;
	protected String description;
	protected String ownerId;
	private PlaceType placeType;
	private Boolean isChildrensPark;
	private Set<String> rides;
	private Boolean isWaterparkAvailable;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "minimum_age_for_rides", joinColumns = @JoinColumn(name = "park_id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "age", length = 50)
	@BatchSize(size = 20)
	private Map<String, Integer> minimumAgeForRides;
	private Double entryFee;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "ride_prices", joinColumns = @JoinColumn(name = "park_id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "prices", length = 50)
	@BatchSize(size = 20)
	private Map<String, Double> ridefeesAndComboPacks;
	@Column(length = 300)
	private String otherDetails;

	public Park() {
		super();
	}

	public Park(String placeName, String address, String description, String ownerId, JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Park;
		this.isChildrensPark = details.get("isChildrensPark").asBoolean();
		String rides = details.get("rides").asText();
		String[] strParts = rides.split(",");
		List<String> listParts = Arrays.asList(strParts);
		this.rides = new HashSet<String>(listParts);
		this.isWaterparkAvailable = details.get("isWaterparkAvailable").asBoolean();
		ObjectMapper mapper = new ObjectMapper();
		this.minimumAgeForRides = mapper.convertValue(details.get("minimumAgeForRides"),
				new TypeReference<Map<String, Integer>>() {
				});
		this.entryFee = details.get("entryFee").asDouble();
		this.ridefeesAndComboPacks = mapper.convertValue(details.get("ridefeesAndComboPacks"),
				new TypeReference<Map<String, Double>>() {
				});
		;
		this.otherDetails = details.get("otherDetails").asText();
	}

	public Park(String placeName, String address, String description, String ownerId, Boolean isChildrensPark,
			Set<String> rides, Boolean isWaterparkAvailable, Map<String, Integer> minimumAgeForRides, Double entryFee,
			Map<String, Double> ridefeesAndComboPacks, String otherDetails) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Park;
		this.isChildrensPark = isChildrensPark;
		this.rides = rides;
		this.isWaterparkAvailable = isWaterparkAvailable;
		this.minimumAgeForRides = minimumAgeForRides;
		this.entryFee = entryFee;
		this.ridefeesAndComboPacks = ridefeesAndComboPacks;
		this.otherDetails = otherDetails;
	}

}
