package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.ItemLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemLocationRepo extends JpaRepository<ItemLocationEntity, Integer> {


}
