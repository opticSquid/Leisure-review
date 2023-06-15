package com.cts.interim.beta.services;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.cts.interim.beta.entities.Hotel;
import com.cts.interim.beta.repositories.HotelRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelService implements MandatoryService<Hotel>{
private final HotelRepo hotelRepo;

@Override
public String addOrUpdateVendor(Hotel vendor) throws IllegalArgumentException{
		return hotelRepo.save(vendor).getId();
}

@Override
public Hotel getVendorById(String id) {
	// TODO Auto-generated method stub
	return hotelRepo.findById(id).orElse(null);
}

@Override
public List<Hotel> getAllVendorsOfSingleOwner(String ownerId) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Hotel> getAllVendors() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Boolean deleteVendorsById(String id) {
	// TODO Auto-generated method stub
	return null;
}

}
