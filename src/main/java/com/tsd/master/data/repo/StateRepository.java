package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsd.master.data.entity.State;
import java.util.List;


public interface StateRepository extends JpaRepository<State, Long> {
	List<State> findByCountryCode(Long countryCode);
}