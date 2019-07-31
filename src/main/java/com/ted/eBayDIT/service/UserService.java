package com.ted.eBayDIT.service;

import com.ted.eBayDIT.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String username);
//    UserDto getUser(String email);
//    UserDto getUserByUserId(String userId);
//    UserDto updateUser(String userId, UserDto user);
//    void deleteUser(String userId);
//    List<UserDto> getUsers(int page, int limit); //returns list of users

}
