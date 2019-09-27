package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.ConnectivityEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.ConnectivityRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.ConnectivityService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectivityServiceImpl implements ConnectivityService {

    @Autowired
    private ConnectivityRepository connectivityRepo;

    @Autowired
    private UserRepository userRepo;


    @Override
    public boolean exist(UserEntity winnerEntity, UserEntity sellerEntity) {

        ConnectivityEntity conn1 = connectivityRepo.findByConnectedUser1AndConnectedUser2(winnerEntity,sellerEntity);
        ConnectivityEntity conn2 = connectivityRepo.findByConnectedUser1AndConnectedUser2(sellerEntity,winnerEntity);
        return conn1 != null || conn2 != null;

    }

    @Override
    public void createConnection(UserEntity winnerEntity, UserEntity sellerEntity) {
        ConnectivityEntity conn2add = new ConnectivityEntity();
        conn2add.setConnectedUser1(winnerEntity);
        conn2add.setConnectedUser2(sellerEntity);
        conn2add.setPending(false);

        connectivityRepo.save(conn2add);
    }


    @Override
    public List<UserDto> getAllContacts(String currUserId) {
        List<UserDto> returnValue = new ArrayList<>();
        List<ConnectivityEntity> connectionsList;

        UserEntity currUser = this.userRepo.findByUserId(currUserId);

        if (currUser == null) throw new RuntimeException("Not existing user with such id!"); //check if is ended

        connectionsList = connectivityRepo.findByConnectedUser1OrConnectedUser2(currUser,currUser); //find all the records in Connective Table tha refer to currUser

        UserEntity connUser1,connUser2;
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        for (ConnectivityEntity connectivityEntity : connectionsList) {
            connUser1 = connectivityEntity.getConnectedUser1();
            connUser2 = connectivityEntity.getConnectedUser2();

            if (! connUser1.equals(currUser) ){ //if true then this is a contact to add
                UserDto userDto = modelMapper.map(connUser1, UserDto.class);
                returnValue.add(userDto);
            }
            if (! connUser2.equals(currUser) ){ //if true then this is a contact to add
                UserDto userDto = modelMapper.map(connUser2, UserDto.class);
                returnValue.add(userDto);
            }

        }


        return returnValue;
    }


}
