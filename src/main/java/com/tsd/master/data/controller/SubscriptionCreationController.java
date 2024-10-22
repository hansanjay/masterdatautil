package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tsd.sdk.request.SubscriptionReq;

import com.tsd.master.data.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/subs")
@Tag(name = "Subscription creation API", description = "Operations related to management of subs")
public class SubscriptionCreationController{
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@PostMapping(path = "/create",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Subsription creation", description = "Operations related to creation of subscription")
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionReq subscriptionReq) {
        return subscriptionService.createSubscription(subscriptionReq);
    }
}