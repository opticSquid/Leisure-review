package com.cts.interim_project.Service_Providers.pojo;

import java.util.List;

import com.cts.interim_project.Service_Providers.entities.Hotel;
import com.cts.interim_project.Service_Providers.entities.Mall;
import com.cts.interim_project.Service_Providers.entities.Park;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessesOwnedByOwner {
	private List<Hotel> hotels;
	private List<Park> parks;
	private List<Mall> malls;
}
