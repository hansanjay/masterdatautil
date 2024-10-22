package com.tsd.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.repo.LocalityRepository;

@Service
public class LocalityService {

	@Autowired
	LocalityRepository localityRepository;

	public ResponseEntity<?> getLocalityList(String filter, String code) {
		switch (filter) {
			case "pincode":
				return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, localityRepository.findByPincode(code)));
			case "cityId":
				return ResponseEntity.ok(
						JsonSuccessResponse.ok("Success", 200, localityRepository.findByCityId(Integer.parseInt(code))));
			default:
				return ResponseEntity.ok(JsonSuccessResponse.ok("No match found", 404, null));
		}
	}
}