package com.tsd.master.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.repo.ProductCategoryRepo;

@Service
public class ProductCategoryService {

	@Autowired
	private ProductCategoryRepo productCategoryRepo;

	public ResponseEntity<?> getProductCatalogueList() {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success",HttpStatus.OK.value(), productCategoryRepo.findByParentId(0)));
	}

	public ResponseEntity<?> getProductCatageoryList(int parentId) {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success",HttpStatus.OK.value(), productCategoryRepo.findByParentId(parentId)));
	}

	public ResponseEntity<?> getProductInfo(Long id) {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success",HttpStatus.OK.value(),productCategoryRepo.findById(id)));
	}
	
}
