package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.Country;
import java.util.List;



@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	List<Country> findByCountryCode(Long countryCode);
}