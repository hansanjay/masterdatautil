package com.tsd.master.data.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tsd.sdk.request.OrderReq;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.entity.Order;
import com.tsd.master.data.entity.Subscription;
import com.tsd.master.data.repo.OrderRepo;

import lombok.SneakyThrows;

@Service
public class OrderDetailsService {
	
	@Autowired
	private OrderRepo orderRepo;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public void generateSubscriptionOrders(Subscription subscription) {
        switch(subscription.getType()) {
        	case 1 : 
        		generateDailyOrders(subscription); 
        		break;
        	case 2 : 
        		generateWeeklyOrders(subscription);
        		break;
        	case 3:
        		generateMonthlyOrders(subscription);
        		break;
        }
    }

	@Transactional
	@SneakyThrows
	private void generateDailyOrders(Subscription subscription) {
		List<Order> orders = new ArrayList<>();
		
		LocalDate startDate = LocalDate.parse(subscription.getStart().toString(), formatter);
		LocalDate endDate = LocalDate.parse(subscription.getStop().toString(), formatter);
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		
		for (int i = 0; i <= daysBetween; i++) {
		    LocalDate orderDate = startDate.plusDays(i);
		    Order dailyOrder = Order.builder()
		    		.address_id(0)
		    		.customer_id(subscription.getCustomerId())
		    		.status(0)
		    		.subscriptionId(subscription.getId())
		    		.order_date(orderDate)
		    		.build();
		    orders.add(dailyOrder);
		}
		orderRepo.saveAll(orders);
	}
	
	@Transactional
	@SneakyThrows
	public void generateWeeklyOrders(Subscription subscription) {
        List<Order> orders = new ArrayList<>();
        DayOfWeek subscriptionDay = DayOfWeek.valueOf(subscription.getDay_of_week().toUpperCase());
        LocalDate firstOrderDate = subscription.getStart().with(TemporalAdjusters.nextOrSame(subscriptionDay));

        while (!firstOrderDate.isAfter(subscription.getStop())) {
        	Order weeklyOrder = Order.builder()
		    		.address_id(0)
		    		.customer_id(subscription.getCustomerId())
		    		.status(0)
		    		.order_date(firstOrderDate)
		    		.subscriptionId(subscription.getId())
		    		.build();
		    orders.add(weeklyOrder);
            firstOrderDate = firstOrderDate.plusWeeks(1);
        }
        orderRepo.saveAll(orders);
    }
	
	@Transactional
	@SneakyThrows
	private void generateMonthlyOrders(Subscription subscription) {
		LocalDate startDate = LocalDate.parse(subscription.getStart().toString(), formatter);
		LocalDate endDate = LocalDate.parse(subscription.getStop().toString(), formatter);
		
		LocalDate firstOrderDate = startDate.withDayOfMonth(Integer.parseInt(subscription.getDay_of_month()));
		
		List<Order> orders = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
        	Order monthlyOrders = Order.builder()
		    		.address_id(0)
		    		.customer_id(subscription.getCustomerId())
		    		.status(0)
		    		.order_date(firstOrderDate)
		    		.subscriptionId(subscription.getId())
		    		.build();
		    orders.add(monthlyOrders);
		    startDate = startDate.plus(1, ChronoUnit.MONTHS);
        }
        orderRepo.saveAll(orders);
	}

	public ResponseEntity<?> fetchOrderDetailsForSubscription(String subsId) {
		List<Order> orders = orderRepo.findBySubscriptionId(Long.parseLong(subsId));
		if(orders.size() > 0) {
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), orders));
		}else {
			return ResponseEntity.ok(JsonSuccessResponse.fail("No orders found", HttpStatus.NOT_FOUND.value(), null));
		}
		
	}

	public ResponseEntity<?> updateOrderStatus(String orderid,String status) {
		Order order = orderRepo.findByOrderId(Long.parseLong(orderid));
		if(null != order) {
			order.setStatus(Integer.parseInt(status));
			orderRepo.save(order);
		}
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), order));
	}

	@Transactional
	@Modifying
	@SneakyThrows
	public void deleteActiveOrders(String custId,Long subscriptionId) {
		orderRepo.deleteFutureOrders(custId, subscriptionId);
	}

	@SneakyThrows
	public ResponseEntity<?> fetchCustOrderDetails(String custId) {
		List<Order> orders = orderRepo.fetchCustOrderDetails(Long.parseLong(custId));
		if(orders.size() > 0) {
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), orders));
		}else {
			return ResponseEntity.ok(JsonSuccessResponse.fail("No orders found", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@SneakyThrows
	public ResponseEntity<?> getOrderById(String id) {
		Order order = orderRepo.findByOrderId(Long.parseLong(id));
		if(null != order) {
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), order));
		}else {
			return ResponseEntity.ok(JsonSuccessResponse.fail("No orders found", HttpStatus.NOT_FOUND.value(), null));
		}
	}

	@SneakyThrows
	@Transactional
	public ResponseEntity<?> updateOrder(String id, OrderReq orderReq) {
		Order order = orderRepo.findByOrderId(Long.parseLong(id));
		LocalDate orderDate = LocalDate.parse(new Date().toString(), formatter);
		if(null != order) {
			order.setAddress_id(0);
			order.setCustomer_id(orderReq.getCustomer_id());
			order.setOrder_date(orderDate);
			order.setStatus(0);
			order.setSubscriptionId(0L);
			
			orderRepo.save(order);
			
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), order));
		}else {
			return ResponseEntity.ok(JsonSuccessResponse.fail("No orders found", HttpStatus.NOT_FOUND.value(), null));
		}
	}
	
	@SneakyThrows
	@Transactional
	public ResponseEntity<?> createOrder(OrderReq orderReq) {
		LocalDate orderDate = LocalDate.parse(new Date().toString(), formatter);
		
		Order order = Order.builder()
				.address_id(orderReq.getAddress_id())
				.customer_id(orderReq.getCustomer_id())
				.order_date(orderDate)
				.status(0)
				.subscriptionId(0L)
				.build();
		
		orderRepo.save(order);
		
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), order));
		
	}

}