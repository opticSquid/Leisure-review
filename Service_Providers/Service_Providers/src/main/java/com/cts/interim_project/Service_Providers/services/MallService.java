package com.cts.interim_project.Service_Providers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.interim_project.Service_Providers.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.commons.ServiceProviders;
import com.cts.interim_project.Service_Providers.repository.MallRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MallService {
	@Autowired
	private MallRepo mallRepo;

	public String addMall(ServiceProviders serviceProviders) {
		Mall newMall = new Mall(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		try {
			Mall savedMall = mallRepo.save(newMall);
			return "mall/" + savedMall.getId();
		} catch (Exception e) {
			log.error("Can not save new Mall, \n{}", e);
			return null;
		}
	}

	public List<Mall> getAllMalls() {
		return mallRepo.findAll();
	}

	public Mall getSingleMall(String id) {
		Optional<Mall> mall = mallRepo.findById(id);
		if (mall.isEmpty()) {
			throw new ProviderNotFoundException("mall not found");
		} else {
			return mall.get();
		}
	}

	public String updateMall(ServiceProviders serviceProviders) {
		try {
			getSingleMall(serviceProviders.getId());
		} catch(ProviderNotFoundException p) {
			log.error("existing mall of given id not found");
			throw p;
		}
		Mall mall = new Mall(serviceProviders.getPlaceName(), serviceProviders.getAddress(),
				serviceProviders.getDescription(), serviceProviders.getOwnerId(), serviceProviders.getDetails());
		mall.setId(serviceProviders.getId());
		log.error("mall to be updated: {}", mall);
		try {
			Mall updatedMall = mallRepo.save(mall);
			return "mall/" + updatedMall.getId();
		} catch (Exception e) {
			log.error("Can not update Mall, \n{}", e);
			return null;
		}
	}

	public void deleteMall(String id) {
		mallRepo.deleteById(id);
	}
}
