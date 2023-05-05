package com.cts.interim_project.Service_Providers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Park;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.repository.ParkRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkService {
	@Autowired
	private ParkRepo parkRepo;

	public String addPark(ServiceProviders serviceProviders) {
		Park newPark = new Park(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		try {
			Park savedPark = parkRepo.save(newPark);
			return "park/" + savedPark.getId();
		} catch (Exception e) {
			log.error("Can not save new Park, \n{}", e);
			return null;
		}
	}

	public List<Park> getAllParks() {
		return parkRepo.findAll();
	}

	public Park getSinglePark(String id) {
		Optional<Park> park = parkRepo.findById(id);
		if (park.isEmpty()) {
			throw new ProviderNotFoundException("park not found");
		} else {
			return park.get();
		}
	}

	public String updatePark(ServiceProviders serviceProviders) {
		try {
			getSinglePark(serviceProviders.getId());
		} catch(ProviderNotFoundException p) {
			log.error("existing park of given id not found");
			throw p;
		}
		Park park = new Park(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		park.setId(serviceProviders.getId());
		log.error("park to be updated: {}", park);
		try {
			Park updatedPark = parkRepo.save(park);
			return "park/" + updatedPark.getId();
		} catch (Exception e) {
			log.error("Can not update Park, \n{}", e);
			return null;
		}
	}

	public void deletePark(String id) {
		parkRepo.deleteById(id);
	}
}
