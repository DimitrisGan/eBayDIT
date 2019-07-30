package com.ted.eBayDIT.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ted.eBayDIT.entity.Role;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByUserRole(String userRole);
}
