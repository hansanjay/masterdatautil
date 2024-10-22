package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.City;
import java.util.List;


@Repository
public interface CityRepo extends JpaRepository<City, Long> {
	List<City> findByStateId(Long stateId);
}
