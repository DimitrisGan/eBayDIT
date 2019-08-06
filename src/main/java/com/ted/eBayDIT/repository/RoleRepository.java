package com.ted.eBayDIT.repository;


import com.ted.eBayDIT.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByUserRole(String userRole);
}
