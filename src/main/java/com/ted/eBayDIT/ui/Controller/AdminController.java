package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/notVerifiedUsers") //ex : /userlist
    public ResponseEntity<Object> getNotVerifiedUsersList(/*@RequestParam(value = "page",defaultValue = "0") int page,
                                                          @RequestParam(value = "limit",defaultValue = "2") int limit,
                                                          @RequestParam(value = "orderBy",defaultValue = "username") int sortBy*/) {

        List<UserRest> returnUsersList = new ArrayList<>();

        List<UserDto> allNotVerifiedUsersList = userService.getAllNotVerifiedUsers();


        ModelMapper modelMapper = new ModelMapper();
        for (UserDto userDto : allNotVerifiedUsersList) {

            UserRest returnUser = modelMapper.map(userDto, UserRest.class);

            returnUsersList.add(returnUser);
        }

        return new ResponseEntity<>(returnUsersList, HttpStatus.OK);

    }

    @GetMapping(path ="/users") //ex : /allUsers
    public ResponseEntity<Object> getUsers(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                           @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
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

    //verify all the users
    @PutMapping(path ="/verify") //verify all users!
    public ResponseEntity<Object> verifyAllUsers() {

        userService.verifyAll();

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


//    @PutMapping(path ="/verifyPartial") //verify some users!
//    public ResponseEntity<Object> verifyPartialUsers(List<UserDetailsRequestModel> userDetailsReq) { //I will take ids? or
//
//        for (UserDetailsRequestModel userDetailsRequestModel : userDetailsReq) {
//            user.
//        }
//        userService.verifyAll();
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//
//    }


    @PutMapping(path ="/verify/{id}") //verify all
    public ResponseEntity<Object> verifyUser(@PathVariable String id ){
        UserDto userDto;

        userDto = this.userService.verifyUser(id);

        ModelMapper modelMapper = new ModelMapper();
        UserRest returnValue = modelMapper.map(userDto, UserRest.class);


        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }



    //todo exportInXML()
}
