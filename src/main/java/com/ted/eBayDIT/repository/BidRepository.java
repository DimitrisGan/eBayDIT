package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidRepository extends JpaRepository<BidEntity, Integer>  {

//    BidEntity findAllBy

}
