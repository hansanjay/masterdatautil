package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.AgentCustPincodeMapping;


@Repository
public interface AgentCustPincodeMappingRepo extends JpaRepository<AgentCustPincodeMapping, Long>{
	
	@Query("SELECT acp FROM AgentCustPincodeMapping acp WHERE acp.agentId = :agentId AND acp.custId = :custId")
	List<AgentCustPincodeMapping> agentCustMappingList(String agentId,String custId);

}