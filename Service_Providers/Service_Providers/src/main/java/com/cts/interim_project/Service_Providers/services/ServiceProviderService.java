package com.cts.interim_project.Service_Providers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.Park;
import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.repository.HotelRepo;
import com.cts.interim_project.Service_Providers.repository.MallRepo;
import com.cts.interim_project.Service_Providers.repository.ParkRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceProviderService {
	@Autowired
	private HotelRepo hotelRepo;
	@Autowired
	private ParkRepo parkRepo;
	@Autowired
	private MallRepo mallRepo;

	public String addServiceProvider(ServiceProviders serviceProviders) {
		if (serviceProviders.getPlaceType() == PlaceType.Hotel) {
			Hotel newHotel = new Hotel(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
					serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
			log.error("New hotel to be saved: {}", newHotel);
			try {
				Hotel savedHotel = hotelRepo.save(newHotel);
				return savedHotel.getId();
			} catch (Exception e) {
				log.error("Can not save new Hotel, \n{}", e);
			}
		} else if (serviceProviders.getPlaceType() == PlaceType.Park) {
			Park newPark = new Park(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
					serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
			try {
				Park savedPark = parkRepo.save(newPark);
				return savedPark.getId();
			} catch (Exception e) {
				log.error("Can not save new Park, \n{}", e);
			}
		} else if (serviceProviders.getPlaceType() == PlaceType.Mall) {
			Mall newMall = new Mall(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
					serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
			try {
				Mall savedMall = mallRepo.save(newMall);
				return savedMall.getId();
			} catch (Exception e) {
				log.error("Can not save new Mall, \n{}", e);
			}
		} else {
			return null;
		}
		return null;
	}
}
