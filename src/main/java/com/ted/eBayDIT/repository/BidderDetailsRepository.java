package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.BidderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidderDetailsRepository extends JpaRepository<BidderDetailsEntity, Integer> {

    BidderDetailsRepository findById(int id);

}
