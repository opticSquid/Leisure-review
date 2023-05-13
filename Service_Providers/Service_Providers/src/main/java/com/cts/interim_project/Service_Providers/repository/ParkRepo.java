package com.cts.interim_project.Service_Providers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.interim_project.Service_Providers.entities.Park;

public interface ParkRepo extends JpaRepository<Park, String> {

}
