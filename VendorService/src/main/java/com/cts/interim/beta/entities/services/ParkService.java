package com.cts.interim.beta.entities.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.interim.beta.entities.Park;
import com.cts.interim.beta.repositories.ParkRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkService implements MandatoryService<Park> {
	private final ParkRepo parkRepo;

	@Override
	public String addOrUodateVendor(Park vendor) {
		// TODO Auto-generated method stub
		return parkRepo.save(vendor).getId();
	}

	@Override
	public Park getVendorById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Park> getAllVendorsOfSingleOwner(String ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Park> getAllVendors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteVendorsById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
