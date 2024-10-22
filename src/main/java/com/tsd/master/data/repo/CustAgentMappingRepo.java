package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.CustAgentMapping;
import java.util.List;



@Repository
public interface CustAgentMappingRepo extends JpaRepository<CustAgentMapping, Long>{
	List<CustAgentMapping> findByCustId(String custId);
	List<CustAgentMapping> findByAgentId(String agentId);
}