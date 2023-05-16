package com.cts.interim.beta.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.interim.beta.entities.ServiceProvider;
import com.cts.interim.beta.entities.services.VendorService;
import com.cts.interim.beta.exceptions.ResourceNotFoundEception;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class NormalUserController {
	private final VendorService vendorService;

	@GetMapping("/get-all-images/{vendorId}")
	public ResponseEntity<List<URI>> getAllPhotos(@PathVariable String vendorId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(vendorService.getAllPhotos(vendorId));
		} catch (Exception ex) {
			throw ex;
		}
	}

	@GetMapping("/images/{vendorId}/{imageName}")
	public ResponseEntity<byte[]> getImage(@PathVariable String vendorId, @PathVariable String imageName)
			throws Exception {
		String imageExtension = null;
		if (imageName.length() <= 3) {
			throw new ResourceNotFoundEception("invalid image name");
		} else {
			imageExtension = imageName.substring(imageName.length() - 3);
		}
		MediaType contentType = imageExtension.equals("jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
		try {
			return ResponseEntity.ok().contentType(contentType).body(vendorService.getPhoto(vendorId, imageName));
		} catch (Exception ex) {
			throw ex;
		}
	}

	@GetMapping("/{vendorId}")
	public ServiceProvider getProviderDetails(@PathVariable String vendorId) {
		return vendorService.findProviderById(vendorId);
	}

	@GetMapping
	public List<ServiceProvider> getAllProviders() {
		return vendorService.fetchAllProviders();
	}

	@GetMapping("/check/{vendorId}")
	public Boolean checkVendorForExistense(@PathVariable String vendorId) {
		return vendorService.ifVendorExists(vendorId);
	}
}
