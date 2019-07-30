package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) {

        //we have to store/save this info to userEntity
        UserEntity userEntity = new UserEntity();


        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword("test");//todo na to allaksw apla
        //todo to xreaizomai gt prepei na valw sto fiels tou encryptedpass timh
        //gt de ginetai na einai null apo db h nullable=false

        userEntity.setUserId("testUserId"); //todo kai auto einai hardcoded


        UserEntity storedUserDetails =  userRepository.save(userEntity);


        //now we have to return this back to our restcontroller
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails ,returnValue);

        return returnValue;
    }

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return null;
//    }
}
