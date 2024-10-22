package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.LocalityService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd/master")
@CrossOrigin
@Tag(name = "List of locality API", description = "Operations related to fetch list of locality")
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @GetMapping("/locality/{filter}/{code}")
    public ResponseEntity<?> getLocalityByStateId(@PathVariable("filter") String filter,@PathVariable("code") String code) {
        return localityService.getLocalityList(filter,code);
    }
}