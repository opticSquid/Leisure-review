package com.cts.interim.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.interim.beta.entities.ServiceProvider;

public interface VendorRepo extends JpaRepository<ServiceProvider, String> {

}
