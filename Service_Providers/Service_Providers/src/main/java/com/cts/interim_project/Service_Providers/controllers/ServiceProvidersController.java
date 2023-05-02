package com.cts.interim_project.Service_Providers.controllers;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim_project.Service_Providers.entities.commons.PlaceType;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.pojo.ServiceProvidersPOJO;
import com.cts.interim_project.Service_Providers.services.FileUploadUtil;
import com.cts.interim_project.Service_Providers.services.ServiceProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		log.error("Json recieved: {}", serviceProider);
		providerService.addServiceProvider(serviceProider);
		// TODO: check the place type using that cast the object to specific class
		return ResponseEntity.status(HttpStatus.SC_OK).body("Object under test");
//		String savedproviderId = providerService.addNewServiceProvider(serviceProvider);
//		if (savedproviderId != null) {
//			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/provider/{id}")
//					.buildAndExpand(savedproviderId).toUri();
//			return ResponseEntity.created(location).build();
//		} else {
//			return ResponseEntity.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).body("Object can not be processed");
//		}
	}

	@PostMapping("/upload/image/{providerId}")
	public ResponseEntity<String> newPlaceImage(@PathVariable String providerId,
			@RequestParam("image") MultipartFile image) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		String upload_dir = "vendor-photos/" + providerId;
		fileUploadUtil.saveFile(upload_dir, fileName, image);
		return ResponseEntity.status(HttpStatus.SC_ACCEPTED).build();
	}
}
