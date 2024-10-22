package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tsd.sdk.request.ProductReq;

import com.tsd.master.data.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/v1/tsd/master/prd")
@Tag(name = "Product creation API", description = "Operations related to Product configuration")
public class ProducController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping(path = "/create",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Produt creation", description = "Operations related to creation of product")
    public ResponseEntity<?> createProduct(@RequestBody ProductReq productReq) {
        return productService.createProduct(productReq);
    }
	
	@PutMapping(path = "/modify/{productId}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Produt update", description = "Operations related to creation of product")
    public ResponseEntity<?> modifyProduct(@PathVariable("productId") String productId,@RequestBody ProductReq productReq) {
        return productService.modifyProduct(productId,productReq);
    }
	
	@PostMapping("/upload/{productId}")
    public ResponseEntity<?> uploadProductImage(@PathVariable("productId") String productId,@RequestParam("file") MultipartFile file) {
        return productService.storeFile(productId,file);
    }
}