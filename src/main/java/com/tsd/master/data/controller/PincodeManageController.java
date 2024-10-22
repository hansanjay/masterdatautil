package com.tsd.master.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tsd.sdk.request.AgentCustPincodeMappingReq;
import org.tsd.sdk.request.AgentPincodeMappingReq;

import com.tsd.master.data.service.PincodeUploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd/master/pincode")
@CrossOrigin
@Tag(name = " Operations related to pincode", description = "API controller for Pincodes related opertaions")
public class PincodeManageController{

	@Autowired
	PincodeUploadService pincodeUploadService;

	@PostMapping("/upload/{distId}")
	@Operation(summary = "Upload Pinode", description = "API allowed to upload the incodes against the Agent")
	public ResponseEntity<?> uploadDistPincodeData(@PathVariable("distId") String distId,@RequestParam("file") MultipartFile file) {
		return pincodeUploadService.uploadDistPincodeData(distId,file);
	}
	
	@GetMapping("/fetch/{distId}")
	@Operation(summary = "Fetch uploaded Pincodes", description = "API allows to fetch all the pincode against the distribiutor by admin")
	public ResponseEntity<?> fetchDistPincodeData(@PathVariable("distId") String agentId) {
		return pincodeUploadService.fetchDistPincodeData(agentId);
	}
	
	@PostMapping("/agentPinCodeMapping")
	@Operation(summary = "API for creating the Agent and Associated Pincode ", description = "Operations related creating the Agent and Associated Pincode")
	public ResponseEntity<?> agentPinCodeMapping(@RequestBody List<AgentPincodeMappingReq> agentPincodeMappingReq ) {
		return pincodeUploadService.agentPinCodeMapping(agentPincodeMappingReq);
	}
	
	@GetMapping("/fetchAgentPinCode/{agentId}")
	@Operation(summary = "List of Pincodes attached with the agent", description = "Operations related to List of Pincodes attached with the pincode")
	public ResponseEntity<?> fetchAgentPinCode(@PathVariable("agentId") String agentId) {
		return pincodeUploadService.fetchAgentPincodeData(agentId);
	}
	
	@GetMapping("/{pincode}/{localityId}")
	@Operation(summary = "Fetch the agent Ids on the basis of Pincode", description = "Operations related to fetch the agent pincode mapping")
	public ResponseEntity<?> fetchPincodeData(@PathVariable("pincode") String pincode,@PathVariable("localityId") String localityId){
		return pincodeUploadService.fetchPincodeData(pincode,localityId);
	}
	
	@PostMapping(path = "/custAgentPinCodeMapping",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "API for creating the Agent Customer and Associated Pincode ", description = "Operations related creating the Agent, Customer and Associated Pincode")
	public ResponseEntity<?> custAgentPinCodeMapping(@RequestBody AgentCustPincodeMappingReq agentCustPincodeMappingReq) {
		return pincodeUploadService.custAgentPinCodeMapping(agentCustPincodeMappingReq);
	}
}