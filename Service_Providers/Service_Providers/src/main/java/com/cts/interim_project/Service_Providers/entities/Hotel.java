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

@Getter
@Setter
@Entity
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	protected String placeName;
	protected String address;
	protected String description;
	protected String ownerId;
	private PlaceType placeType;
	private Integer numberOfRooms;
	private Set<String> roomCategories;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "hotel_prices", joinColumns = @JoinColumn(name = "hotel_id"))
	@MapKeyColumn(name = "rooms", length = 100)
	@Column(name = "prices", length = 50)
	@BatchSize(size = 20)
	private Map<String, Double> pricings;
	private Boolean isVegAvailable;
	private String offDays;
	private List<String> otherServices;

	public Hotel() {
		super();
	}

	public Hotel(String placeName, String address, String description, String ownerId, JsonNode details) {
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Hotel;
		this.numberOfRooms = details.get("numberOfRooms").asInt();
		String roomCategories = details.get("roomCategories").asText();
		String[] strParts = roomCategories.split(",");
		List<String> listParts = Arrays.asList(strParts);
		this.roomCategories = new HashSet<String>(listParts);
		ObjectMapper mapper = new ObjectMapper();
		this.pricings = mapper.convertValue(details.get("pricings"), new TypeReference<Map<String, Double>>() {
		});
		this.isVegAvailable = details.get("isVegAvailable").asBoolean();
		this.offDays = details.get("offDays").asText();
		this.otherServices = mapper.convertValue(details.get("otherServices"), new TypeReference<List<String>>() {
		});
	}

	public Hotel(String placeName, String address, String description, String ownerId, Integer numberOfRooms,
			Set<String> roomCategories, Map<String, Double> pricings, Boolean isVegAvailable, String offDays,
			List<String> otherServices) {
		super();
		this.placeName = placeName;
		this.address = address;
		this.description = description;
		this.ownerId = ownerId;
		this.placeType = PlaceType.Hotel;
		this.numberOfRooms = numberOfRooms;
		this.roomCategories = roomCategories;
		this.pricings = pricings;
		this.isVegAvailable = isVegAvailable;
		this.offDays = offDays;
		this.otherServices = otherServices;
	}

	@Override
	public String toString() {
		return "Hotel [numberOfRooms=" + numberOfRooms + ", roomCategories=" + roomCategories + ", pricings=" + pricings
				+ ", isVegAvailable=" + isVegAvailable + ", offDays=" + offDays + ", otherServices=" + otherServices
				+ ", placeName=" + placeName + ", address=" + address + ", description=" + description + ", ownerId="
				+ ownerId + "]";
	}
}
