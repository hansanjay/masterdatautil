package com.tsd.master.data.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.tsd.sdk.request.AgentCustPincodeMappingReq;
import org.tsd.sdk.request.AgentPincodeMappingReq;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsd.master.data.entity.AgentCustPincodeMapping;
import com.tsd.master.data.entity.AgentPincodeMapping;
import com.tsd.master.data.entity.Pincode;
import com.tsd.master.data.repo.AgentCustPincodeMappingRepo;
import com.tsd.master.data.repo.AgentPincodeMappingRepository;
import com.tsd.master.data.repo.PincodeDistRepository;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

@Service
public class PincodeUploadService {

	@Autowired
	private PincodeDistRepository pincodeDistRepository;
	
	@Autowired
	private AgentPincodeMappingRepository agentPincodeMappingRepository;
	
	@Autowired
	private AgentCustPincodeMappingRepo agentCustPincodeMappingRepo;
	
	@Autowired
	private RestTemplate restTemplate;

	@SneakyThrows
	public ResponseEntity<?> uploadDistPincodeData(String distId,MultipartFile file){
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
			Set<Pincode> pincodes = new LinkedHashSet<Pincode>();
			for (CSVRecord csvRecord : csvParser) {
				List<Object[]> findDistPincodeMapping = pincodeDistRepository.findDistPincodeMapping(csvRecord.get(0), distId);
				if(findDistPincodeMapping.size() == 0) {
					Pincode pincode = Pincode.builder()
							.distrbutorId(distId)
							.pincode(csvRecord.get(0))
							.allocated(0)
							.localityId(Integer.parseInt(csvRecord.get(1)))
							.build();
					pincodes.add(pincode);
				}
			}
			if(!pincodes.isEmpty()){
				pincodeDistRepository.saveAll(pincodes);
			}
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), pincodes));
		}
	}

	public ResponseEntity<?> fetchDistPincodeData(String agentId) {
		List<Object[]> pincodes = pincodeDistRepository.fetchAgentsOnPincodes(agentId);
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), pincodes));
	}

	@Transactional
	@SneakyThrows
	public ResponseEntity<?> agentPinCodeMapping(List<AgentPincodeMappingReq> agentPincodeMappingReqs) {
		List<AgentPincodeMapping> list = agentPincodeMappingRepository.findByAgentId(agentPincodeMappingReqs.get(0).getAgentId());
		
		if(!list.isEmpty()) {
			agentPincodeMappingRepository.deleteByAgentId(agentPincodeMappingReqs.get(0).getAgentId());
		}
		list = new ArrayList<AgentPincodeMapping>();
		
		for (AgentPincodeMappingReq record : agentPincodeMappingReqs) {
			AgentPincodeMapping agentPincodeMapping = AgentPincodeMapping.builder()
					.agentId(record.getAgentId())
					.pincode(record.getPincode())
					.allocated(1)
					.localityId(record.getLocalityId())
					.build();
			list.add(agentPincodeMapping);
		}
		agentPincodeMappingRepository.saveAll(list);
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), null));
	}

	@SneakyThrows
	public ResponseEntity<?> fetchPincodeData(String pincode, String localityId) {

		String custServiceURL = "http://localhost:8183/api/v1/tsd/cust/fetchAgentByCustbyId/";
		ResponseEntity<JsonSuccessResponse> response = null;
		List<Object> agentResponseDTOs = new ArrayList<>();
		List<Object[]> objects = agentPincodeMappingRepository.findByPincodeAndLocality(pincode, localityId);
		Iterator<Object[]> obj = objects.iterator();
		if(obj.hasNext()) {
			String id = (String) obj.next()[0];
			custServiceURL = custServiceURL +id;
			response = restTemplate.getForEntity(custServiceURL, JsonSuccessResponse.class);
			System.out.println(response.getBody().data);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			agentResponseDTOs.add(response.getBody().data);
		}
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), agentResponseDTOs));
	}

	public ResponseEntity<?> fetchAgentPincodeData(String agentId) {
		return ResponseEntity.ok(
				JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), pincodeDistRepository.fetchAgentsOnPincodes(agentId)));
	}

	public ResponseEntity<?> custAgentPinCodeMapping(AgentCustPincodeMappingReq agentCustPincodeMappingReq) {
		List<AgentCustPincodeMapping> agentCustMappingList = agentCustPincodeMappingRepo.agentCustMappingList(agentCustPincodeMappingReq.getAgentId(), agentCustPincodeMappingReq.getCustId());
		if(agentCustMappingList.size() > 1) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("Agent Customer mapping already exists", HttpStatus.ALREADY_REPORTED.value(), null));
		}else {
			AgentCustPincodeMapping agentCustPincodeMapping = AgentCustPincodeMapping.builder()
					.agentId(agentCustPincodeMappingReq.getAgentId())
					.custId(agentCustPincodeMappingReq.getCustId())
					.pincode(agentCustPincodeMappingReq.getPincode())
					.addressId(agentCustPincodeMappingReq.getAddressId())
					.build();
			agentCustPincodeMappingRepo.save(agentCustPincodeMapping);
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), agentCustPincodeMapping));
		}
	}
	
}