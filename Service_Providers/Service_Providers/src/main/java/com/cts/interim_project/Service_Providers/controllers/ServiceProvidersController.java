package com.cts.interim_project.Service_Providers.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.Park;
import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.services.FileUploadUtil;
import com.cts.interim_project.Service_Providers.services.ServiceProviderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/providers")
@Slf4j
public class ServiceProvidersController {
	@Autowired
	private ServiceProviderService providerService;
	@Autowired
	private FileUploadUtil fileUploadUtil;

	@PostMapping("/new")
	public ResponseEntity<String> newServiceProvider(@RequestBody ServiceProviders serviceProider) {
		String serviceProviderId = providerService.addServiceProvider(serviceProider);
		if (serviceProviderId != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/providers/{id}")
					.buildAndExpand(serviceProviderId).toUri();
			return ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).body("Object can not be processed");
		}
	}

	@PostMapping("/upload/image/{providerId}")
	public ResponseEntity<String> newPlaceImage(@PathVariable String providerId,
			@RequestParam("image") MultipartFile image) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		String upload_dir = "service-provider-photos/" + providerId;
		fileUploadUtil.saveFile(upload_dir, fileName, image);
		return ResponseEntity.status(HttpStatus.SC_ACCEPTED).build();
	}

	@GetMapping("/hotels")
	public ResponseEntity<List<Hotel>> getHotels() {
		List<Hotel> hotels = providerService.getAllHotels();
		return ResponseEntity.status(HttpStatus.SC_OK).body(hotels);
	}

	@GetMapping("/parks")
	public ResponseEntity<List<Park>> getParks() {
		List<Park> parks = providerService.getAllParks();
		return ResponseEntity.status(HttpStatus.SC_OK).body(parks);
	}

	@GetMapping("/malls")
	public ResponseEntity<List<Mall>> getMalls() {
		List<Mall> malls = providerService.getAllMalls();
		return ResponseEntity.status(HttpStatus.SC_OK).body(malls);
	}

	@GetMapping("/hotels/{id}")
	public ResponseEntity<Object> getHotel(@PathVariable String id) {
		try {
			return ResponseEntity.status(HttpStatus.SC_OK).body(providerService.getHotelById(id));
		} catch (ProviderNotFoundException p) {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(p.getMessage());
		}
	}

	@GetMapping("/parks/{id}")
	public ResponseEntity<Object> getPark(@PathVariable String id) {
		try {
			return ResponseEntity.status(HttpStatus.SC_OK).body(providerService.getParkById(id));
		} catch (ProviderNotFoundException p) {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(p.getMessage());
		}
	}

	@GetMapping("/malls/{id}")
	public ResponseEntity<Object> getMall(@PathVariable String id) {
		try {
			return ResponseEntity.status(HttpStatus.SC_OK).body(providerService.getMallById(id));
		} catch (ProviderNotFoundException p) {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(p.getMessage());
		}
	}

	@GetMapping("/check/{id}")
	public Boolean checkForServiceProvider(@PathVariable String id) {
		return providerService.isServiceProviderValid(id);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateServiceProvider(@RequestBody ServiceProviders serviceProider) {
		try {
			String serviceProviderId = providerService.updateServiceProvider(serviceProider);
			if (serviceProviderId != null) {
				URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/providers/{id}")
						.buildAndExpand(serviceProviderId).toUri();
				return ResponseEntity.created(location).build();
			} else {
				return ResponseEntity.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).body("Object can not be processed");
			}
		} catch (ProviderNotFoundException p) {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(p.getMessage());
		}
	}

	@DeleteMapping("/delete/{type}/{id}")
	public ResponseEntity<String> deleteProvider(@PathVariable("type") String type, @PathVariable("id") String id) {
		log.error("type: {}, id: {}", type, id);
		if (type.equals("hotel")) {
			providerService.deleteServiceProvider(PlaceType.Hotel, id);
			return ResponseEntity.status(HttpStatus.SC_OK).body("Provider deleted");
		} else if (type.equals("park")) {
			providerService.deleteServiceProvider(PlaceType.Park, id);
			return ResponseEntity.status(HttpStatus.SC_OK).body("Provider deleted");
		} else if (type.equals("mall")) {
			providerService.deleteServiceProvider(PlaceType.Mall, id);
			return ResponseEntity.status(HttpStatus.SC_OK).body("Provider deleted");
		} else {
			return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
		}
	}
}
