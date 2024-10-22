package com.tsd.master.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tsd.master.data.entity.Category;
import java.util.List;


@Repository
public interface ProductCategoryRepo extends JpaRepository<Category, Long> {
	List<Category> findByParentId(int parentId);
}