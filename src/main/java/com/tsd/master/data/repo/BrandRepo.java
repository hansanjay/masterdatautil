package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsd.master.data.entity.Brand;

@Repository
public interface  BrandRepo extends JpaRepository<Brand,Long>{

}
