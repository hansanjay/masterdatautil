package com.tsd.master.data.entity;

import java.time.LocalDate;

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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscription")
@Entity
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int brandId;
	private int productType;
	private int category;
	private int subCategory;
	private int quantity;
	private int type;
	private int status;
	private Long customerId;
	private Long distributorId;
	private boolean permanent;
    private boolean visible;
    private LocalDate start;
    private LocalDate stop;
    private String day_of_week;
    private String day_of_month;
    
}