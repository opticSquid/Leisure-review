package com.cts.interim.beta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.interim.beta.entities.Park;

public interface ParkRepo extends JpaRepository<Park, String> {

}
