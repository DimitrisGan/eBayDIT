package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.ConnectivityEntity;
import com.ted.eBayDIT.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectivityRepository extends JpaRepository<ConnectivityEntity, Integer> {

    ConnectivityEntity findByConnectedUser1AndConnectedUser2(UserEntity user1,  UserEntity user2);

    List<ConnectivityEntity> findByConnectedUser1OrConnectedUser2(UserEntity user1, UserEntity user2);

}
