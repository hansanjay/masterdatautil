package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.CityService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd/master")
@CrossOrigin
@Tag(name = "List of states API", description = "Operations related to fetch list of states")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/city/{stateId}")
    public ResponseEntity<?> getStatesByCountryId(@PathVariable("stateId") Long stateId) {
        return cityService.getCitiesByStateId(stateId);
    }
}