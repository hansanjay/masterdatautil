package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.StateService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd")
@CrossOrigin
@Tag(name = "List of states API", description = "Operations related to fetch list of states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping("/states/{country_code}")
    public ResponseEntity<?> getStatesByCountryId(@PathVariable("country_code") Long country_code) {
        return stateService.getStatesByCountryId(country_code);
    }
    
    @GetMapping("/fetchadd/{filter}/{id}")
    public ResponseEntity<?> getStatesById(@PathVariable("filter") String filter,@PathVariable("id") Long id) {
        return stateService.getStatesById(filter,id);
    }
}