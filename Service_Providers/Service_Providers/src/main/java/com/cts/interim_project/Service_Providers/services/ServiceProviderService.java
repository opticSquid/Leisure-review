package com.cts.interim_project.Service_Providers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.Park;
import com.cts.interim_project.Service_Providers.entities.commons.NewServiceProviderPOJO;
import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.Role;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.entities.commons.ValidateRequest;
import com.cts.interim_project.Service_Providers.entities.commons.ValidateResponse;
import com.cts.interim_project.Service_Providers.pojo.BusinessesOwnedByOwner;
import com.netflix.discovery.converters.Auto;

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
	@Autowired
	private RestTemplate restTemplate;

	private ResponseEntity<ValidateResponse> validateUser(String token, String email) {
		log.debug("token: {}, email: {}", token, email);
		return restTemplate.postForEntity("http://USERS/users/auth/validate",
				new ValidateRequest(token.substring(7), email), ValidateResponse.class);
	}
	
	public String addServiceProvider(String token, NewServiceProviderPOJO newServiceprovider) {
		ResponseEntity<ValidateResponse> validuser = validateUser(token, newServiceprovider.getBusinessEmail());
		// changing the current user role to service_owner to differentiate from normal
		// user account
		ResponseEntity<Boolean> isRoleChanged = restTemplate.postForEntity("http://USERS/users/auth/change-role",
				validuser.getBody().getUserId(), Boolean.class);
		if (validuser.getBody().getUserId() != null && validuser.getStatusCode().is2xxSuccessful()
				&& isRoleChanged.getBody()) {
			// TODO: put data in the constructor
			ServiceProviders serviceProviders = new ServiceProviders(newServiceprovider.getPlaceName(),
					newServiceprovider.getAddress(), newServiceprovider.getDescription(),
					validuser.getBody().getUserId(), newServiceprovider.getPlaceType(),
					newServiceprovider.getDetails());
			if (serviceProviders.getPlaceType() == PlaceType.Hotel) {
				return hotelService.addHotel(serviceProviders);
			} else if (serviceProviders.getPlaceType() == PlaceType.Park) {
				return parkService.addPark(serviceProviders);
			} else {
				return mallService.addMall(serviceProviders);
			}
		} else {
			throw new ProviderNotFoundException(
					"the service provider having a legit account is not found. Please check the bsiness email that is being provided");
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
		// used by review service
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
	
	public BusinessesOwnedByOwner getAllBusinessesOfTheOwner(String token, String businessEmail){
		ResponseEntity<ValidateResponse> validUser = validateUser(token, businessEmail);
		if(validUser.getBody().getUserId()!=null && validUser.getBody().getRole()==Role.SERVICE_OWNER) {
			//TODO: return all the businesses owned by the user one by one
		}
	}

	public String updateServiceProvider(ServiceProviders serviceProviders) throws ProviderNotFoundException {
		// TODO: check if owner is valid
		// TODO: and if the given business belongs to the owner
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
		// TODO: check if owner is valid
		// TODO: and if the given business belongs to the owner
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
