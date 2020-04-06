package com.example.repository;

import java.util.ArrayList;

import com.example.entity.Category;
import com.example.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("subcategoryRepository")
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	
//	Subcategory findById(long id);
	Subcategory findByName(String name);
	ArrayList<Subcategory> findAllByCategory(Category category);
}
