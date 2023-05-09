package com.cts.interim_project.Service_Providers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.Park;
import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServiceProviderService {
	@Autowired
	private HotelService hotelService;
	@Autowired
	private ParkService parkService;
	@Autowired
	private MallService mallService;

	public String addServiceProvider(ServiceProviders serviceProviders) {
		if (serviceProviders.getPlaceType() == PlaceType.Hotel) {
			return hotelService.addHotel(serviceProviders);
		} else if (serviceProviders.getPlaceType() == PlaceType.Park) {
			return parkService.addPark(serviceProviders);
		} else if (serviceProviders.getPlaceType() == PlaceType.Mall) {
			return mallService.addMall(serviceProviders);
		} else {
			return null;
		}
	}

	public List<Hotel> getAllHotels() {
		return hotelService.getAllHotels();
	}

	public List<Park> getAllParks() {
		return parkService.getAllParks();
	}

	public List<Mall> getAllMalls() {
		return mallService.getAllMalls();
	}

	public Hotel getHotelById(String id) {
		return hotelService.getSingleHotel(id);
	}

	public Park getParkById(String id) {
		return parkService.getSinglePark(id);
	}

	public Mall getMallById(String id) {
		return mallService.getSingleMall(id);
	}

	public Boolean isServiceProviderValid(String id) {
		// check if the id is present in either of the tables
		try {
			hotelService.getSingleHotel(id);
			return true;
		} catch (ProviderNotFoundException p) {
			try {
				parkService.getSinglePark(id);
				return true;
			} catch (ProviderNotFoundException pnfe) {
				try {
					mallService.getSingleMall(id);
					return true;
				} catch (ProviderNotFoundException pnf) {
					return false;
				}
			}
		}

	}

	public String updateServiceProvider(ServiceProviders serviceProviders) throws ProviderNotFoundException {
		if (serviceProviders.getPlaceType() == PlaceType.Hotel) {
			return hotelService.updateHotel(serviceProviders);
		} else if (serviceProviders.getPlaceType() == PlaceType.Park) {
			return parkService.updatePark(serviceProviders);
		} else if (serviceProviders.getPlaceType() == PlaceType.Mall) {
			return mallService.updateMall(serviceProviders);
		} else {
			return null;
		}
	}

	public void deleteServiceProvider(PlaceType type, String id) {
		log.error("In service: type: {}, id: {}", type, id);
		if (type == PlaceType.Hotel) {
			hotelService.deleteHotel(id);
		} else if (type == PlaceType.Park) {
			parkService.deletePark(id);
		} else {
			mallService.deleteMall(id);
		}
	}
}
