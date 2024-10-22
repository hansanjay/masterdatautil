package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.CustomerAgentMappingService;


@RestController
@RequestMapping("api/v1/tsd/master")
@CrossOrigin
public class CustomerAgentMappingController {
	
	@Autowired
	CustomerAgentMappingService customerAgentMappingService;

	@PostMapping(path = "/custagentmapping/{agentId}/{custId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustagentmapping(@PathVariable("agentId") String agentId,@PathVariable("custId") String custId){
		return customerAgentMappingService.createCustagentmapping(agentId,custId);
	}
	
	@GetMapping(path = "/fetchagentforcust/{custId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchagentforcust(@PathVariable("custId") String custId){
		return customerAgentMappingService.fetchAgentForCust(custId);
	}
	
	@GetMapping(path = "/fetchcustforagent/{agentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchCustForAgent(@PathVariable("agentId") String agentId){
		return customerAgentMappingService.fetchCustForAgent(agentId);
	}
	
}