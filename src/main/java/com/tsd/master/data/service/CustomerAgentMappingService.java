package com.tsd.master.data.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsd.master.data.entity.CustAgentMapping;
import com.tsd.master.data.repo.CustAgentMappingRepo;

import lombok.SneakyThrows;

@Service
public class CustomerAgentMappingService {

	@Autowired
	private CustAgentMappingRepo custAgentMappingRepo;
	
	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<?> createCustagentmapping(String agentId,String custId) {
		CustAgentMapping custAgentMapping = CustAgentMapping.builder()
				.agentId(agentId)
				.custId(custId)
				.build();
		
		custAgentMappingRepo.save(custAgentMapping);
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, custAgentMapping));
	}

	@SneakyThrows
	public ResponseEntity<?> fetchAgentForCust(String custId) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonSuccessResponse<?> jsonResponse = null;
		List<JsonSuccessResponse<?>> jsonResponseList = new ArrayList<JsonSuccessResponse<?>>();

		List<CustAgentMapping> fetchAgentData = custAgentMappingRepo.findByCustId(custId);
		Iterator<CustAgentMapping> iterator = fetchAgentData.iterator();

		while (iterator.hasNext()) {
			CustAgentMapping custAgentMapping = iterator.next();
			String url = "http://localhost:8183/api/v1/tsd/cust/fetchAgentByCustbyId/" + custAgentMapping.getAgentId();
			System.out.println("@fetchAgentForCust @@@@@@ ::::: " + url);
			ResponseEntity<?> response = restTemplate.getForEntity(url, JsonSuccessResponse.class);
			String jsonString = objectMapper.writeValueAsString(response.getBody());
			System.out.println(response.getBody());
			jsonResponse = objectMapper.readValue(jsonString, JsonSuccessResponse.class);
			System.out.println("fetchAgentForCust @@@@@@@@ ::::: " + jsonResponse.data);
			jsonResponseList.add(jsonResponse);
		}

		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, jsonResponseList));
	}

//	public ResponseEntity<?> fetchCustForAgent(String agentId) {
//		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, custAgentMappingRepo.findByAgentId(agentId)));
//	}
	
	@SneakyThrows
	public ResponseEntity<?> fetchCustForAgent(String agenId) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonSuccessResponse<?> jsonResponse = null;
		List<JsonSuccessResponse<?>> jsonResponseList = new ArrayList<JsonSuccessResponse<?>>();

		List<CustAgentMapping> findByAgentIdList = custAgentMappingRepo.findByAgentId(agenId);
		Iterator<CustAgentMapping> iterator = findByAgentIdList.iterator();

		while (iterator.hasNext()) {
			CustAgentMapping custAgentMapping = iterator.next();
			String url = "http://localhost:8183/api/v1/tsd/cust/fetchCustByAgentId/" + custAgentMapping.getCustId();
			System.out.println("@fetchAgentForCust @@@@@@ ::::: " + url);
			ResponseEntity<?> response = restTemplate.getForEntity(url, JsonSuccessResponse.class);
			String jsonString = objectMapper.writeValueAsString(response.getBody());
			System.out.println(response.getBody());
			jsonResponse = objectMapper.readValue(jsonString, JsonSuccessResponse.class);
			System.out.println("fetchAgentForCust @@@@@@@@ ::::: " + jsonResponse.data);
			jsonResponseList.add(jsonResponse);
		}

		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, jsonResponseList));
	}

}