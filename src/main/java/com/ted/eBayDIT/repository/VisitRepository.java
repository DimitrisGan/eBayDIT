package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity,Integer> {



}
