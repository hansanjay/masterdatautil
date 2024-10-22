package com.tsd.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.repo.BrandRepo;

@Service
public class BrandService {

	@Autowired
	private BrandRepo brandRepo;

	public ResponseEntity<?> getAllBrandList() {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, brandRepo.findAll()));
	}
	
}
