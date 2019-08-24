package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {


}