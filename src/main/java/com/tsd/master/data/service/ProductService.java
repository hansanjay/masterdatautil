package com.tsd.master.data.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tsd.sdk.request.ProductReq;
import org.tsd.sdk.response.JsonSuccessResponse;

import com.tsd.master.data.entity.Product;
import com.tsd.master.data.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired 
	private ProductRepo productRepo;
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	
	@Value("${file.maxFileSize}")
	private String maxFileSize;


	public ResponseEntity<?> createProduct(ProductReq productReq) {
		
		Product product = Product.builder()
				.brandId(Integer.parseInt(productReq.getBrandId()))
				.category(productReq.getCategory())
				.features(productReq.getFeatures())
				.mrp(Double.parseDouble(productReq.getMrp()))
				.packagingType(productReq.getPackagingType())
				.prdType(productReq.getPrdType())
				.return_policy(productReq.getReturn_policy())
				.shelfLife(productReq.getShelfLife())
				.subCategory(productReq.getSubCategory())
				.title(productReq.getTitle())
				.unit(Integer.parseInt(productReq.getUnit()))
				.unitDisplay(productReq.getUnitDisplay())
				.discount(Double.parseDouble(productReq.getDiscount()))
				.ssu(Integer.parseInt(productReq.getSsu()))
				.build();
		
		productRepo.save(product);
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), product));
	}

	public ResponseEntity<?> fetchProducts() {
		return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), productRepo.findAll()));
	}

	public ResponseEntity<?> modifyProduct(String productId,ProductReq productReq) {
		Optional<Product> obj = productRepo.findById(Long.parseLong(productId));
		if(!obj.isPresent()) {
			return ResponseEntity.ok(JsonSuccessResponse.fail("Fail", HttpStatus.NOT_FOUND.value(), null));
		}else {
			Product product = obj.get();
			product.setBrandId(Integer.parseInt(productReq.getBrandId()));
			product.setCategory(productReq.getCategory());
			product.setFeatures(productReq.getFeatures());
			product.setMrp(Double.parseDouble(productReq.getMrp()));
			product.setPackagingType(productReq.getPackagingType());
			product.setPrdType(productReq.getPrdType());
			product.setReturn_policy(productReq.getReturn_policy());
			product.setShelfLife(productReq.getShelfLife());
			product.setSubCategory(productReq.getSubCategory());
			product.setTitle(productReq.getTitle());
			product.setUnit(Integer.parseInt(productReq.getUnit()));
			product.setUnitDisplay(productReq.getUnitDisplay());
			product.setSsu(Integer.parseInt(productReq.getSsu()));
			product.setDiscount(Double.parseDouble(productReq.getDiscount()));
			
			productRepo.save(product);
			
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), product));
		}
	}
	
    public ResponseEntity<?> storeFile(String productId,MultipartFile file){
    	uploadDir = uploadDir+"/"+productId;
        Path uploadPath = Paths.get(uploadDir);
        try {
			if (!Files.exists(uploadPath)) {
			    Files.createDirectories(uploadPath);
			}
			if(file.getSize() > Long.parseLong(maxFileSize)) {
				return ResponseEntity.ok(JsonSuccessResponse.fail("File must be less then 200KB", HttpStatus.NOT_ACCEPTABLE.value(), null));
			}
			
			String fileName = file.getOriginalFilename();
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath);
			
			return ResponseEntity.ok(JsonSuccessResponse.ok("Success", HttpStatus.OK.value(), fileName));
			
		} catch (IOException e) {
			return ResponseEntity.ok(JsonSuccessResponse.fail(e.getMessage(), HttpStatus.NOT_ACCEPTABLE.value(), null));
		}
    }

}