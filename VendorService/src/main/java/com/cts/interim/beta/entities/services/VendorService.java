package com.cts.interim.beta.entities.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cts.interim.beta.config.ValidateRequest;
import com.cts.interim.beta.config.ValidateResponse;
import com.cts.interim.beta.entities.Hotel;
import com.cts.interim.beta.entities.Mall;
import com.cts.interim.beta.entities.Park;
import com.cts.interim.beta.entities.PlaceType;
import com.cts.interim.beta.entities.ServiceProvider;
import com.cts.interim.beta.exceptions.DataCouldNotbeSavedException;
import com.cts.interim.beta.exceptions.UserNotValidException;
import com.cts.interim.beta.repositories.VendorRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VendorService {
	private final VendorRepo vendorRepo;
	private final RestTemplate restTemplate;
	private final HotelService hotelService;
	private final ParkService parkService;
	private final MallService mallService;

	private ResponseEntity<ValidateResponse> validateUser(String token, String email) {
		try {
			return restTemplate.postForEntity("http://USERS/users/auth/validate",
					new ValidateRequest(token.substring(7), email), ValidateResponse.class);
		} catch (HttpClientErrorException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ValidateResponse(null, null, "jwt error"));
		}

	}

	private ResponseEntity<Boolean> changeRole(String userId) {
		return restTemplate.postForEntity("http://USERS/users/auth/change-role", userId, Boolean.class);
	}

	public String addVendor(String token, ServiceProvider vendor) {
		ResponseEntity<ValidateResponse> validUser = validateUser(token, vendor.getEmail());
		ResponseEntity<Boolean> isUserRoleChanged = changeRole(validUser.getBody().getUserId());
		if (validUser.getStatusCode().is2xxSuccessful() && validUser.getBody().getRole() != null
				&& isUserRoleChanged.getBody()) {
			ValidateResponse user = validUser.getBody();
			vendor.setOwnerId(user.getUserId());
			vendor.setDetails(vendor.getDetails());
			if (vendor.getPlaceType() == PlaceType.hotel) {
				Hotel newHotel = new Hotel(vendor);
				try {
					return "hotel/" + hotelService.addOrUpdateVendor(newHotel);
				} catch (IllegalArgumentException ex) {
					throw new DataCouldNotbeSavedException("data could not be processed successfully",
							new Throwable("the hotel data going to database was 'null'", ex.fillInStackTrace()));
				}
			} else if (vendor.getPlaceType() == PlaceType.park) {
				Park newPark = new Park(vendor);
				try {
					return "park/" + parkService.addOrUpdateVendor(newPark);
				} catch (IllegalArgumentException ex) {
					throw new DataCouldNotbeSavedException("data could not be processed successfully",
							new Throwable("the park data going to database was 'null'", ex.fillInStackTrace()));
				}

			} else {
				Mall newMall = new Mall(vendor);
				try {
					return "mall/" + mallService.addOrUpdateVendor(newMall);
				} catch (IllegalArgumentException ex) {
					throw new DataCouldNotbeSavedException("data could not be processed successfully",
							new Throwable("the mall data going to database was 'null'", ex.fillInStackTrace()));
				}

			}
		} else {
			throw new UserNotValidException("permission denied", new Throwable(
					"the current user identified by the Authorization header token is not valid or the user does not have the permission to perform this operation"));
		}
	}
}
