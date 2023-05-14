package com.cts.interim.beta.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Park extends ServiceProvider {
	private Boolean isChildrensPark;
	private Set<String> rides;
	private Boolean isWaterparkAvailable;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "minimum_age_for_rides", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "age", length = 50)
	@BatchSize(size = 20)
	private Map<String, Integer> minimumAgeForRides;
	private Double entryFee;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "ride_prices", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "prices", length = 50)
	@BatchSize(size = 20)
	private Map<String, Double> ridefeesAndComboPacks;
	@Column(length = 300)
	private String otherDetails;

	public Park(ServiceProvider vendor) {
		super(vendor.getId(), vendor.getPlaceName(), vendor.getAddress(), vendor.getOwnerId());
		this.isChildrensPark = vendor.getDetails().get("isChildrensPark").asBoolean();
		String rides = vendor.getDetails().get("rides").asText();
		String[] strParts = rides.split(",");
		List<String> listParts = Arrays.asList(strParts);
		this.rides = new HashSet<String>(listParts);
		this.isWaterparkAvailable = vendor.getDetails().get("isWaterparkAvailable").asBoolean();
		ObjectMapper mapper = new ObjectMapper();
		this.minimumAgeForRides = mapper.convertValue(vendor.getDetails().get("minimumAgeForRides"),
				new TypeReference<Map<String, Integer>>() {
				});
		this.entryFee = vendor.getDetails().get("entryFee").asDouble();
		this.ridefeesAndComboPacks = mapper.convertValue(vendor.getDetails().get("ridefeesAndComboPacks"),
				new TypeReference<Map<String, Double>>() {
				});
		;
		this.otherDetails = vendor.getDetails().get("otherDetails").asText();
	}
}
