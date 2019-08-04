package com.ted.eBayDIT.service;

import com.ted.eBayDIT.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    int createUser(UserDto user);
    UserDto updateUser(String userId,UserDto user2update);

    UserDto getUser(String username);
    UserDto getUserByUserId(String userId);
    boolean userExists(String username);

    void deleteUser(String userId);
//    UserDto getUser(String email);
//    UserDto updateUser(String userId, UserDto user);
    List<UserDto> getUsers(); //returns list of users

}
