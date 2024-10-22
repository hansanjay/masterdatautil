package com.tsd.master.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.entity.City;
import com.tsd.master.data.entity.Locality;
import com.tsd.master.data.entity.State;
import com.tsd.master.data.repo.CityRepo;
import com.tsd.master.data.repo.LocalityRepository;
import com.tsd.master.data.repo.StateRepository;

import lombok.SneakyThrows;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepo cityRepo;
    
    @Autowired
    private LocalityRepository localityRepository;

    @SneakyThrows
	public ResponseEntity<?> getStatesByCountryId(Long code) {
        return ResponseEntity.ok(JsonSuccessResponse.ok("Success", 200, stateRepository.findByCountryCode(code)));
    }

	public ResponseEntity<?> getStatesById(String filter, Long state_id) {
		if("state".equals(filter)) {
			Optional<State> obj  = stateRepository.findById(state_id);
			if(obj == null) {
				return ResponseEntity.ok(JsonSuccessResponse.fail("No Data Match", HttpStatus.NOT_FOUND.value(),null));
			}else {
				State state = obj.get();
				return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(),state.getStateName()));
			}
		}else if("city".equals(filter)){
			Optional<City> obj  = cityRepo.findById(state_id);
			if(obj == null) {
				return ResponseEntity.ok(JsonSuccessResponse.fail("No Data Match", HttpStatus.NOT_FOUND.value(),null));
			}else {
				City city = obj.get();
				return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(),city.getCityName()));
			}
		}else if("locality".equals(filter)){
			Optional<Locality> obj  = localityRepository.findById(state_id);
			if(obj == null) {
				return ResponseEntity.ok(JsonSuccessResponse.fail("No Data Match", HttpStatus.NOT_FOUND.value(),null));
			}else {
				Locality locality = obj.get();
				return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(),locality.getLocalityName()));
			}
		}
		return null;
	}
}