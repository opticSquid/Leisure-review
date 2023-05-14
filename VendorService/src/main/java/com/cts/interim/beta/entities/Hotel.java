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
public class Hotel extends ServiceProvider {
	private Integer numberOfRooms;
	private Set<String> roomCategories;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "hotel_prices", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "rooms", length = 100)
	@Column(name = "prices", length = 50)
	@BatchSize(size = 20)
	private Map<String, Double> pricings;
	private Boolean isVegAvailable;
	private String offDays;
	private List<String> otherServices;

	public Hotel(ServiceProvider vendor) {
		super(vendor.getId(), vendor.getPlaceName(), vendor.getAddress(), vendor.getOwnerId());
		this.numberOfRooms = vendor.getDetails().get("numberOfRooms").asInt();
		String roomCategories = vendor.getDetails().get("roomCategories").asText();
		String[] strParts = roomCategories.split(",");
		List<String> listParts = Arrays.asList(strParts);
		this.roomCategories = new HashSet<String>(listParts);
		ObjectMapper mapper = new ObjectMapper();
		this.pricings = mapper.convertValue(vendor.getDetails().get("pricings"),
				new TypeReference<Map<String, Double>>() {
				});
		this.isVegAvailable = vendor.getDetails().get("isVegAvailable").asBoolean();
		this.offDays = vendor.getDetails().get("offDays").asText();
		this.otherServices = mapper.convertValue(vendor.getDetails().get("otherServices"),
				new TypeReference<List<String>>() {
				});
	}

}
