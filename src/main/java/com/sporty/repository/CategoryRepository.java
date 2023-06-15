package com.sporty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sporty.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
