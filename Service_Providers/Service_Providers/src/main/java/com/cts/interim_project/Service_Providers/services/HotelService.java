package com.cts.interim_project.Service_Providers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.repository.HotelRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelService {
	@Autowired
	private HotelRepo hotelRepo;

	public String addHotel(ServiceProviders serviceProviders) {
		Hotel newHotel = new Hotel(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		log.error("New hotel to be saved: {}", newHotel);
		try {
			Hotel savedHotel = hotelRepo.save(newHotel);
			return "hotel/" + savedHotel.getId();
		} catch (Exception e) {
			log.error("Can not save new Hotel, \n{}", e);
			return null;
		}
	}

	public List<Hotel> getAllHotels() {
		return hotelRepo.findAll();
	}

	public Hotel getSingleHotel(String id) {
		Optional<Hotel> hotel = hotelRepo.findById(id);
		if (hotel.isEmpty()) {
			throw new ProviderNotFoundException("hotel not found");
		} else {
			return hotel.get();
		}
	}

	public String updateHotel(ServiceProviders serviceProviders) {
		try {
			getSingleHotel(serviceProviders.getId());
		} catch(ProviderNotFoundException p) {
			log.error("existing hotel of given id not found");
			throw p;
		}
		Hotel hotel = new Hotel(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		hotel.setId(serviceProviders.getId());
		log.error("hotel to be updated: {}", hotel);
		try {
			Hotel updatedHotel = hotelRepo.save(hotel);
			return "hotel/" + updatedHotel.getId();
		} catch (Exception e) {
			log.error("Can not update Hotel, \n{}", e);
			return null;
		}
	}

	public void deleteHotel(String id) {
		hotelRepo.deleteById(id);
	}
}
