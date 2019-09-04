package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.ItemLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemLocationRepository extends JpaRepository<ItemLocationEntity, Integer> {


}
