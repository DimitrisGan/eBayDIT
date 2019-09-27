package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByNameAndParentId(String name,int parentId);

    CategoryEntity findByName(String name);

    //example1:    @Query("SELECT u FROM User u WHERE u.status = 1")
    //example2:    @Query(value = "select c from category c where c.parentId = -1")
    @Query(
            value = "SELECT * FROM category c WHERE c.parent_id = -1",
            nativeQuery = true)
    List<CategoryEntity> findRootCategories();

    List<CategoryEntity> findByLevel(int level);

    @Query(
            value = "SELECT * FROM category c WHERE c.parent_id = :parentId",
            nativeQuery = true)

    List<CategoryEntity> findChildrenCategories(@Param("parentId") Integer parentId);
}
