package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsd.master.data.entity.Locality;

public interface LocalityRepository extends JpaRepository<Locality, Long> {
	List<Locality> findByLocalityId(Long localityId);
	List<Locality> findByPincode(String pincode);
	List<Locality> findByCityId(int cityId);
}