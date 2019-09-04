package com.ted.eBayDIT.repository;

import com.ted.eBayDIT.entity.MessageEntity;
import com.ted.eBayDIT.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findBySenderAndReceiver(UserEntity sender ,UserEntity receiver );

}