package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.OrderDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/master/order")
@Tag(name = "Order API", description = "Operations related to order details")
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService orderDetailsService;
	
	@GetMapping(path = "/fetch/{subsId}")
	@Operation(summary = "Order List for subscription", description = "Operations related to list of orders for the subscription")
    public ResponseEntity<?> fetchOrderDetails(@PathVariable("subsId") String subsId) {
        return orderDetailsService.fetchOrderDetailsForSubscription(subsId);
    }
	
	@PutMapping(path = "/status/{orderid}/{status}")
	@Operation(summary = "Update Order status", description = "Operations related to update the status of order for the customer")
    public ResponseEntity<?> createSubscription(@PathVariable("orderid") String orderid,@PathVariable("status") String status) {
        return orderDetailsService.updateOrderStatus(orderid,status);
    }
	
}
