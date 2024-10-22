package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.AgentPincodeMapping;

@Repository
public interface AgentPincodeMappingRepository extends JpaRepository<AgentPincodeMapping, Long>{
	
	@Query("SELECT p.agentId FROM AgentPincodeMapping p where p.pincode=:pincode and p.localityId=:localityId")
	List<Object[]> findByPincodeAndLocality(String pincode,String localityId);
	
	List<AgentPincodeMapping> findByAgentId(String agentId);
	void deleteByAgentId(String agentId);
}