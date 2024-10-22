package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.Pincode;

@Repository
public interface PincodeDistRepository extends JpaRepository<Pincode, Long>{
	@Query("SELECT p.pincode,l.localityName,l.id FROM Locality l JOIN Pincode p ON l.pincode = p.pincode WHERE p.distrbutorId = :distrbutorId")
	List<Object[]> findByDistrbutorId(String distrbutorId);
	
	List<Pincode> findByPincode(String pincode);
	
	@Query("SELECT p.pincode,l.localityName,l.id FROM Locality l JOIN AgentPincodeMapping p ON l.pincode = p.pincode WHERE p.agentId = :agentId")
	List<Object[]> fetchAgentsOnPincodes(@Param("agentId") String agentId);
	
	@Query("SELECT p.pincode,l.localityName,l.id FROM Locality l JOIN Pincode p ON l.pincode = p.pincode WHERE p.pincode = :pincode")
	List<Object[]> fetchAgentsForCustomer(@Param("pincode") String pincode);
	
}