package com.tsd.master.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int brandId;
	private String title;
	private double mrp;
	
	private String prdType;
	private String category;
	private String subCategory;
	
	private String shelfLife;
	private String unitDisplay;
	private int unit;
	private int ssu;
	private double discount;
	
	private String packagingType;
	private String return_policy;
	private String features;
	
}
