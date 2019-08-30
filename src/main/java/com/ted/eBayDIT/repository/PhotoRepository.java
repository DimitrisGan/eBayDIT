package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity,Integer> {



}
