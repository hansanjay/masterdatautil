package com.tsd.master.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.Order;


@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
	List<Order> findBySubscriptionId(long subscriptionId);
	
	@Query("Select o from Order o where o.id=:id")
	Order findByOrderId(@Param("id") Long id);
	
	//@Query("SELECT o FROM Order o WHERE o.customer_id = :custId and o.subscriptionId=:subscriptionId and o.dateField > CURRENT_DATE")
	@Query("DELETE FROM Order o WHERE o.customer_id = :custId and o.subscriptionId=:subscriptionId and o.order_date > CURRENT_DATE")
	void deleteFutureOrders(String custId,Long subscriptionId);

}