package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.BrandService;
import com.tsd.master.data.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/master/prd")
@Tag(name = "Product List", description = "Operations related to fetch product list")
public class ProducFetchController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping(path = "/fetch/{val}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Fetch Product List", description = "API to fetch all the Product available for subscription")
    public ResponseEntity<?> fetchProducts(@PathVariable("val") String val) {
        return productService.fetchProducts();
    }
	
	@GetMapping(path = "/fetchBrand/{val}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Fetch BrandList List", description = "API to fetch all the Brands available for subscription")
    public ResponseEntity<?> fetchBrand(@PathVariable("val") String val) {
        return brandService.getAllBrandList();
    }
	
}