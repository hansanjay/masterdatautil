package com.tsd.master.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tsd.sdk.request.SubscriptionReq;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.entity.Subscription;
import com.tsd.master.data.repo.SubscriptionRepo;

import lombok.SneakyThrows;

@Service
public class SubscriptionService {
	
	
	@Autowired
	private SubscriptionRepo subscriptionRepo;
	
	@Autowired 
	private OrderDetailsService orderDetailsService;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Transactional
	@SneakyThrows
	public ResponseEntity<?> createSubscription(SubscriptionReq subscriptionReq) {
		
		if(subscriptionReq.getStop().isBefore(subscriptionReq.getStart())) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("End Date must be greater then the start Date", HttpStatus.NOT_FOUND.value(), null));
		}

		Subscription subscription = Subscription.builder()
				.brandId(subscriptionReq.getBrandId())
				.productType(subscriptionReq.getProductType())
				.category(subscriptionReq.getCategory())
				.subCategory(subscriptionReq.getSubCategory())
				.customerId(Long.parseLong(subscriptionReq.getCustomerId()))
				.distributorId(Long.parseLong(subscriptionReq.getDistributorId()))
				.type(subscriptionReq.getType())
				.day_of_week(subscriptionReq.getDay_of_week())
				.day_of_month(subscriptionReq.getDay_of_month())
				.status(subscriptionReq.getStatus())
				.start(LocalDate.parse(subscriptionReq.getStart().toString(), formatter))
				.stop(LocalDate.parse(subscriptionReq.getStop().toString(), formatter))
				.quantity(subscriptionReq.getQuantity())
				.permanent(subscriptionReq.isPermanent())
				.visible(subscriptionReq.isVisible())
				.build();
		
		subscription = subscriptionRepo.save(subscription);
		
		orderDetailsService.generateSubscriptionOrders(subscription);

		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), subscription));
	}

	@SneakyThrows
	public ResponseEntity<?> activateSubscription(String subscriptionId) {
		Optional<Subscription> obj = subscriptionRepo.findById(Long.parseLong(subscriptionId));
		if(!obj.isPresent()) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("Fail", HttpStatus.NOT_FOUND.value(), null));
		}else {
			Subscription subscription = obj.get();
			subscription.setVisible(true);
			subscription.setStatus(1);
			subscriptionRepo.save(subscription);
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), subscription));
		}
	}
	
	@SneakyThrows
	public ResponseEntity<?> deActivateSubscription(String subscriptionId) {
		Optional<Subscription> obj = subscriptionRepo.findById(Long.parseLong(subscriptionId));
		if(!obj.isPresent()) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("Fail", HttpStatus.NOT_FOUND.value(), null));
		}else {
			Subscription subscription = obj.get();
			subscription.setVisible(false);
			subscription.setStatus(0);
			subscriptionRepo.save(subscription);
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), subscription));
		}
	}
	
	@SneakyThrows
	public ResponseEntity<?> modifySubscription(String subscriptionId,SubscriptionReq subscriptionReq) {
		Optional<Subscription> obj = subscriptionRepo.findById(Long.parseLong(subscriptionId));
		if(!obj.isPresent()) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("Fail", HttpStatus.NOT_FOUND.value(), null));
		}else {
			
			if(subscriptionReq.getStop().isBefore(subscriptionReq.getStart())) {
				return ResponseEntity.ok(JsonSuccessResponse.fail("End Date must be greater then the start Date", HttpStatus.NOT_FOUND.value(), null));
			}
			
			Subscription subscription = obj.get();
			subscription.setBrandId(subscriptionReq.getBrandId());
			subscription.setProductType(subscriptionReq.getProductType());
			subscription.setCategory(subscriptionReq.getCategory());
			subscription.setSubCategory(subscriptionReq.getSubCategory());
			
			subscription.setType(subscriptionReq.getType());
			subscription.setDay_of_week(subscriptionReq.getDay_of_week());
			subscription.setDay_of_month(subscriptionReq.getDay_of_month());
			
			subscription.setStatus(subscriptionReq.getStatus());
			subscription.setStart(LocalDate.parse(subscriptionReq.getStart().toString(), formatter));
			subscription.setStop(LocalDate.parse(subscriptionReq.getStop().toString(), formatter));
			subscription.setQuantity(subscriptionReq.getQuantity());
			subscription.setPermanent(subscriptionReq.isPermanent());
			subscription.setVisible(subscriptionReq.isVisible());
			
			subscriptionRepo.save(subscription);
			
			orderDetailsService.deleteActiveOrders(subscriptionId,subscription.getCustomerId());
			
			orderDetailsService.generateSubscriptionOrders(subscription);
			
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), subscription));
		}
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@SneakyThrows
	public ResponseEntity<?> fetchSubscriptionForCustomer(String customerId) {
		List<Subscription> subscriptions = subscriptionRepo.findByCustomerId(customerId);
		if(subscriptions.size() > 0) {
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), subscriptions));
		}else {
			return ResponseEntity.ok(JsonSuccessResponse.fail("No data Found", HttpStatus.NOT_FOUND.value(), null));
		}
	}

}