package com.cts.interim_project.Service_Providers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.repository.HotelRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceProviderService {
	@Autowired
	private HotelRepo hotelRepo;

	public void addServiceProvider(ServiceProviders serviceProviders) {
		if (serviceProviders.getPlaceType() == PlaceType.Hotel) {
			Hotel newHotel = new Hotel(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
					serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
			log.error("New hotel to be saved: {}",newHotel);
			try {
				hotelRepo.save(newHotel);
			} catch (Exception e) {
				log.error("Can not save new Hotel, \n{}", e);
			}
		}
	}
}
