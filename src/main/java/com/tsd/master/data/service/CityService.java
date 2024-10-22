package com.tsd.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.repo.CityRepo;

@Service
public class CityService {
	
	@Autowired
	private CityRepo cityRepo;

	public ResponseEntity<?> getCitiesByStateId(Long stateId) {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, cityRepo.findByStateId(stateId)));
	}

}
