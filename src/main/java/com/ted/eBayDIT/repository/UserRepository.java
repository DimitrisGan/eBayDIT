package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//repository class needed for persistance

@Repository
public interface  UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);



    /*userID is the public id*/
    UserEntity findByUserId(String userId);


    UserEntity deleteByUserId(String userId);

    //todo logika tha xwsw kai alla px findByLastName

    List<UserEntity> findByVerifiedFalse();//VerifiedFalse();
}
