package com.tsd.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.repo.CountryRepository;

import lombok.SneakyThrows;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @SneakyThrows
	public ResponseEntity<?> getAllCountries() {
        return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, countryRepository.findAll()));
    }
}