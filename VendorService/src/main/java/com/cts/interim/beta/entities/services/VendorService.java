package com.cts.interim.beta.entities.services;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.cts.interim.beta.config.Role;
import com.cts.interim.beta.config.ValidateRequest;
import com.cts.interim.beta.config.ValidateResponse;
import com.cts.interim.beta.entities.Hotel;
import com.cts.interim.beta.entities.Mall;
import com.cts.interim.beta.entities.Park;
import com.cts.interim.beta.entities.PlaceType;
import com.cts.interim.beta.entities.ServiceProvider;
import com.cts.interim.beta.exceptions.DataCouldNotbeSavedException;
import com.cts.interim.beta.exceptions.ResourceNotFoundEception;
import com.cts.interim.beta.exceptions.UserNotValidException;
import com.cts.interim.beta.exceptions.UserOperationNotPermitted;
import com.cts.interim.beta.repositories.VendorRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VendorService {
	private final VendorRepo vendorRepo;
	private final FileUploadUtil fileUploadUtil;
	private final RestTemplate restTemplate;
	private final HotelService hotelService;
	private final ParkService parkService;
	private final MallService mallService;

	private ResponseEntity<ValidateResponse> validateUser(String token) {
		try {
			return restTemplate.postForEntity("http://USERS/users/auth/validate",
					new ValidateRequest(token.substring(7)), ValidateResponse.class);
		} catch (HttpClientErrorException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ValidateResponse(null, null, "jwt error"));
		}
	}

	private ResponseEntity<Boolean> changeRole(String userId) {
		return restTemplate.postForEntity("http://USERS/users/auth/change-role", userId, Boolean.class);
	}

	public String addVendor(String token, ServiceProvider vendor) {
		ResponseEntity<ValidateResponse> validUser = validateUser(token);
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

	public ServiceProvider findProviderById(String id) {
		return vendorRepo.findById(id).orElse(null);
	}

	public Boolean ifVendorExists(String id) {
		if (findProviderById(id) != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<ServiceProvider> fetchAllProviders() {
		return vendorRepo.findAll();
	}

	public Boolean deleteVendor(String token, String id) {
		ResponseEntity<ValidateResponse> validUser = validateUser(token);
		ServiceProvider vendor = findProviderById(id);
		if (vendor != null && validUser.getBody().getRole() == Role.SERVICE_OWNER) {
			vendorRepo.deleteById(id);
			return true;
		} else {
			throw new UserNotValidException("permission denied", new Throwable(
					"the current user identified by the id is not valid or the user does not have the permission to perform this operation"));
		}
	}

	public void uploadPhoto(String token, String vendorId, MultipartFile image) throws IOException {
		ResponseEntity<ValidateResponse> validUser = validateUser(token);
		ServiceProvider provider = findProviderById(vendorId);
		if (validUser.getBody().getUserId() != null && validUser.getBody().getRole() == Role.SERVICE_OWNER
				&& provider != null) {
			String fileName = StringUtils.cleanPath(image.getOriginalFilename()).replaceAll("\\s+", "");
			System.out.println("File name: " + fileName);
			String upload_dir = "vendor-photos/" + provider.getPlaceType().toString() + "/" + provider.getId();
			fileUploadUtil.saveFile(upload_dir, fileName, image);
		} else if (validUser.getBody().getUserId() != null && validUser.getBody().getRole() == Role.USER) {
			throw new UserOperationNotPermitted(
					"user does not have the nessecery permission to perform this operation");
		} else if (validUser.getBody().getUserId() == null) {
			throw new UserNotValidException("permission denied",
					new Throwable("the current user identified by the Authorization header token is not valid"));
		} else if (provider == null) {
			throw new DataCouldNotbeSavedException("vendor with given id is not found",
					new Throwable("the vendor id that was provided was not found in the database"));
		}
	}

	public byte[] getPhoto(String vendorId, String imageName) throws IOException {
		ServiceProvider vendor = findProviderById(vendorId);
		if (vendor != null) {
			return fileUploadUtil.fetchSingleImage(vendor.getPlaceType(), vendorId, imageName);
		} else {
			throw new UserNotValidException("user does not exist", new Throwable("user with given id does not exist"));
		}

	}

	public List<URI> getAllPhotos(String vendorId) {
		ServiceProvider vendor = findProviderById(vendorId);
		if (vendor != null) {
			List<URI> allImages = fileUploadUtil.getAllPhotos(vendor.getPlaceType(), vendorId);
			log.error("All images: {}", allImages);
			if (allImages == null) {
				throw new ResourceNotFoundEception("All the images requested for was not found in the server");
			} else {
				return allImages;
			}
		} else {
			throw new UserNotValidException("user does not exist", new Throwable("user with given id does not exist"));
		}
	}
}
