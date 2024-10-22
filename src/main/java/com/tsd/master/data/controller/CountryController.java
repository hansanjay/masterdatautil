package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.CountryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd/master")
@CrossOrigin
@Tag(name = "List of countries API API", description = "Operations related to fetch list of countries")
public class CountryController {

    @Autowired
    private CountryService countryService;
    
    @GetMapping(path = "/health")
	public ResponseEntity<?> health(){
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @GetMapping(path = "/getcountries/{val}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCountries(@PathVariable("val") String val) {
        return countryService.getAllCountries();
    }
}