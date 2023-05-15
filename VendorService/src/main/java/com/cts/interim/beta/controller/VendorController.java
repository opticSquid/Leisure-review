package com.cts.interim.beta.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim.beta.entities.ServiceProvider;
import com.cts.interim.beta.entities.services.VendorService;
import com.google.common.net.HttpHeaders;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendors/admin")
@RequiredArgsConstructor
public class VendorController {
	private final VendorService vendorService;

	private URI UriBuilder(String dynamicPart) {
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/vendors/{dynamicPart}")
				.buildAndExpand(dynamicPart).toUri();
		return location;
	}

	@PostMapping("/add-new-service")
	public ResponseEntity<String> addNew(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
			@RequestBody ServiceProvider newVendor) {
		try {
			return ResponseEntity.created(UriBuilder(vendorService.addVendor(token, newVendor))).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping("/upload-image/{vendorId}")
	public ResponseEntity<String> newPlaceImage(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
			@PathVariable String vendorId, @RequestParam("image") MultipartFile image) throws IOException  {
		try {
			vendorService.uploadPhoto(token, vendorId, image);
		} catch (IOException ex) {
			throw ex;
		}
		return ResponseEntity.status(HttpStatus.SC_ACCEPTED).build();
	}

}
