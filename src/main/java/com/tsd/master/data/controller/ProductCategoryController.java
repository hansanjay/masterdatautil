package com.tsd.master.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsd.master.data.service.ProductCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tsd")
@CrossOrigin
@Tag(name = "Product category", description = "Operations related to fetch list of product categories")
public class ProductCategoryController {

   @Autowired
   private ProductCategoryService productCategoryService;
   
   @GetMapping(path = "/product/catalogue/{val}",produces = MediaType.APPLICATION_JSON_VALUE)
   @Operation(summary = "Product Catalogue", description = "API to fecth the list of available catalogues")
   public ResponseEntity<?> getProductCatalogue(@PathVariable("val") String val) {
       return productCategoryService.getProductCatalogueList();
   }
   
   @GetMapping("/product/catagory/{parentId}")
   @Operation(summary = "Product Catalgoery", description = "API to fecth the list of available catagories for the perticular catalogue.")
   public ResponseEntity<?> getProductCatageory(@PathVariable("parentId") int parentId) {
       return productCategoryService.getProductCatageoryList(parentId);
   }
   
   @GetMapping("/product/{id}")
   @Operation(summary = "Product Info", description = "API to fecth the the product info.")
   public ResponseEntity<?> getProductInfo(@PathVariable("id") String id) {
       return productCategoryService.getProductInfo(Long.parseLong(id));
   }
	
}