package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.utility.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public UserDto createUser(UserDto user) {


        //check if username already exists in db
//        UserEntity storedUserDetails = userRepository.findByUsername(user.getUsername());
//        if (storedUserDetails != null) throw new RuntimeException("Record(username) already exists");

        //we have to store/save this info to userEntity
        UserEntity userEntity = new UserEntity();


        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));//todo na to allaksw apla
        //todo to xreaizomai gt prepei na valw sto fiels tou encryptedpass timh
        //gt de ginetai na einai null apo db h nullable=false


        String publicUserId =utils.generateUserId(30);
        userEntity.setUserId(publicUserId); //todo kai auto einai hardcoded

        UserEntity storedUserDetails =  userRepository.save(userEntity);


        //now we have to return this back to our restcontroller
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails ,returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getUsername(),userEntity.getEncryptedPassword(),new ArrayList<>()); //ArrayList stands for the authorities
    }
}
