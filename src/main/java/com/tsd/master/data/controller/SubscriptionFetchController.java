package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/master/subs")
@Tag(name = "Subscription API", description = "Operations related to List of subscriptions")
public class SubscriptionFetchController{
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping(path = "/fetch/{customerId}")
	@Operation(summary = "Subsription List", description = "Operations related to list of subscriptions for the customer")
    public ResponseEntity<?> createSubscription(@PathVariable("customerId") String customerId) {
        return subscriptionService.fetchSubscriptionForCustomer(customerId);
    }
}