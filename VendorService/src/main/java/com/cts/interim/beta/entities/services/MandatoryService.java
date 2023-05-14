package com.cts.interim.beta.entities.services;

import java.util.List;

public interface MandatoryService<T> {
	String addOrUodateVendor(T vendor);

	T getVendorById(String id);

	List<T> getAllVendorsOfSingleOwner(String ownerId);

	List<T> getAllVendors();

	Boolean deleteVendorsById(String id);
}
