package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tsd.sdk.request.SubscriptionReq;

import com.tsd.master.data.service.SubscriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/master/subs")
@Tag(name = "Subscription status API", description = "Operations related to handle subscription operations")
public class SubscriptionModifyController{
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@PutMapping(path = "/activate/{subscriptionId}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Activate Subscription", description = "API responsible activation of subscription")
    public ResponseEntity<?> activateSubscription(@PathVariable("subscriptionId") String subscriptionId) {
        return subscriptionService.activateSubscription(subscriptionId);
    }
	
	@PutMapping(path = "/deactivate/{subscriptionId}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Deactivate Subscription", description = "API responsible deactivation of subscription")
    public ResponseEntity<?> deSubscription(@PathVariable("subscriptionId") String subscriptionId) {
        return subscriptionService.deActivateSubscription(subscriptionId);
    }
	
	@PutMapping(path = "/modify/{subscriptionId}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Modify Subscription", description = "API responsible modification in subscription")
    public ResponseEntity<?> modifySubscription(@PathVariable("subscriptionId") String subscriptionId,@RequestBody SubscriptionReq subscriptionReq) {
        return subscriptionService.modifySubscription(subscriptionId,subscriptionReq);
    }
	
}