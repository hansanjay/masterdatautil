package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.Subscription;


@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long>{
	
	@Query("SELECT s FROM Subscription s WHERE s.customerId = :custId")
	List<Subscription> findByCustomerId(String custId);
}