package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//todo I need to query all users that verified=false
@RestController
@RequestMapping("admin")
public class AdminController {


    //todo getUsers that are not verified
    //todo getDetails of user
    //todo post /{id} user to verify him

    @Autowired
    UserService userService;

    @GetMapping("/userlist")
    public ResponseEntity<Object> getNotVerifiedUsersList(@RequestParam(value = "page",defaultValue = "0") int page,
                                                          @RequestParam(value = "limit",defaultValue = "2") int limit,
                                                          @RequestParam(value = "orderBy",defaultValue = "username") int sortBy) {

        List<UserRest> returnUsersList = new ArrayList<>();

        List<UserDto> allNotVerifiedUsersList = userService.getNotVerifiedUsers();



        ModelMapper modelMapper = new ModelMapper();
        for (UserDto userDto : allNotVerifiedUsersList) {

            UserRest returnUser = modelMapper.map(userDto, UserRest.class);

            returnUsersList.add(returnUser);
        }


//        return returnUsersList;
        return new ResponseEntity<>(returnUsersList, HttpStatus.OK);

    }

    @GetMapping(path ="/allUsers") //todo it should go to adminController not here
    public ResponseEntity<Object> getUsers(@RequestParam(value = "page",defaultValue = "1") int pageNo,
                                           @RequestParam(value = "limit",defaultValue = "2") int pageSize,
                                           @RequestParam(value = "orderBy",defaultValue = "username") String sortBy) {


        List<UserDto> list = userService.getAllUsers(pageNo, pageSize, sortBy);

        List<UserRest> returnUsersList =new ArrayList<>();


        ModelMapper modelMapper = new ModelMapper();
        for (UserDto userDto : list) {

            UserRest returnUser = modelMapper.map(userDto, UserRest.class);
            returnUsersList.add(returnUser);
        }


        return new ResponseEntity<>(returnUsersList, HttpStatus.OK);

    }

//        UserListOutputModel output = new UserListOutputModel();
//        for (UserEntity u : allUsers) {
//            try {
//                output.addUser(userEntityService.getUserOutputModelFromUser(u));
//            } catch (IOException e) {
//                return new ResponseEntity<>("Could not load images", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }




//    @GetMapping("/dashboard")
//    public ResponseEntity<Object> dashboard(){
//
//
////        return new ResponseEntity<>(output, HttpStatus.OK);
//    }
//
//    @GetMapping("/userDetails")
//    public ResponseEntity<Object> getUserDetails(){
//
//
////        return new ResponseEntity<>(output, HttpStatus.OK);
//    }
//
//
//
//    @PostMapping("/verify/{id}")
//    public ResponseEntity<Object> dashboard(){
//
//
////        return new ResponseEntity<>(output, HttpStatus.OK);
//    }

}
