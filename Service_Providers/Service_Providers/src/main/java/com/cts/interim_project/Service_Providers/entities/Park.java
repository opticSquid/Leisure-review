package com.cts.interim_project.Service_Providers.entities;

import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;

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
public class Park extends ServiceProviders {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private Boolean isChildrensPark;
	private Set<String> rides;
	private Boolean isWaterparkAvailable;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "minimum_age_for_rides", joinColumns = @JoinColumn(name = "park_id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "age", length = 50)
	@BatchSize(size = 20)
	private Map<String, Integer> minimumAgeForRides;
	private Float entryFee;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "ride_prices", joinColumns = @JoinColumn(name = "park_id"))
	@MapKeyColumn(name = "rides", length = 100)
	@Column(name = "prices", length = 50)
	@BatchSize(size = 20)
	private Map<String, Float> ridefeesAndComboPacks;
	@Column(length = 300)
	private String otherDetails;

	public Park(String placeName, String address, String description, String ownerId, Boolean isChildrensPark,
			Set<String> rides, Boolean isWaterparkAvailable, Map<String, Integer> minimumAgeForRides, Float entryFee,
			Map<String, Float> ridefeesAndComboPacks, String otherDetails) {
		super(placeName, address, description, ownerId);
		this.isChildrensPark = isChildrensPark;
		this.rides = rides;
		this.isWaterparkAvailable = isWaterparkAvailable;
		this.minimumAgeForRides = minimumAgeForRides;
		this.entryFee = entryFee;
		this.ridefeesAndComboPacks = ridefeesAndComboPacks;
		this.otherDetails = otherDetails;
	}
}
