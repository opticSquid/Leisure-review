package com.cts.interim.beta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.interim.beta.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {
	@Query("select h from Hotel h where h.ownerId=?1")
	List<Hotel> getHotelsOfsameOwner(String ownerId);
}
