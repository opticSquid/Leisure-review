package com.cts.interim.beta.entities.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.interim.beta.entities.Mall;
import com.cts.interim.beta.repositories.MallRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MallService implements MandatoryService<Mall> {
	private final MallRepo mallRepo;

	@Override
	public String addOrUodateVendor(Mall vendor) {
		// TODO Auto-generated method stub
		return mallRepo.save(vendor).getId();
	}

	@Override
	public Mall getVendorById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mall> getAllVendorsOfSingleOwner(String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mall> getAllVendors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteVendorsById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
