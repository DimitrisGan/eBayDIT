package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.response.UserRest;
import com.ted.eBayDIT.utility.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


        //todo check if username already exists in db
//        UserEntity storedUserDetails = userRepository.findByUsername(user.getUsername());
//        if (storedUserDetails != null) throw new RuntimeException("Record(username) already exists");

        //we have to store/save this info to userEntity
        UserEntity userEntity2save = new UserEntity();


        BeanUtils.copyProperties(user,userEntity2save);

        userEntity2save.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));


        String publicUserId =utils.generateUserId(30);
        userEntity2save.setUserId(publicUserId);

        UserEntity storedUserDetails =  userRepository.save(userEntity2save);


        //now we have to return this back to our restcontroller
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails ,returnValue);

        return returnValue;
    }

    @Override
    public UserDto  updateUser(String userId ,UserDto user2update) {

        UserDto returnValue =new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        //todo more fields should be available for update
        if (user2update.getEmail()      != null)    {userEntity.setEmail(user2update.getEmail()); }
        if (user2update.getFirstName()  != null)    {userEntity.setFirstName(user2update.getFirstName()); }
        if (user2update.getLastName()   != null)    {userEntity.setLastName(user2update.getLastName()); }


        UserEntity updatedUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails,returnValue);
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

    //    public List<Us> getUser





    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue =new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }

    @Override
    public boolean userExists(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username);

        return userEntity != null; //if userEntity is null return false
    }

    //    @Transactional
    @Override
    public int deleteUser(String userId) {

        UserEntity userEntity = this.userRepository.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }
        this.userRepository.delete(userEntity);

        return 0;
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> returnUsersList = new ArrayList<>();

        List<UserEntity> usersEntity =this.userRepository.findAll();

        for (UserEntity userEntity : usersEntity) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity,userDto);
            returnUsersList.add(userDto);
        }
        return returnUsersList;
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
