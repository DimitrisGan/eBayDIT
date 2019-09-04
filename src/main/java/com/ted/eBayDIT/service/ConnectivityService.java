package com.ted.eBayDIT.service;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;

import java.util.List;

public interface ConnectivityService {


    boolean exist(UserEntity winnerEntity, UserEntity sellerEntity);

    void createConnection(UserEntity winnerEntity, UserEntity sellerEntity);

    List<UserDto> getAllContacts(String currUserId);

}
