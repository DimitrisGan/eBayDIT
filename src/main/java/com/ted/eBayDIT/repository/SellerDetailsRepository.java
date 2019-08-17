package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.SellerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SellerDetailsRepository extends JpaRepository<SellerDetailsEntity, Integer> {

//    SellerDetailsEntity findAllBy
}
