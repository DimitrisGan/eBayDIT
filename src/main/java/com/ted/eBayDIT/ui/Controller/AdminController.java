package com.ted.eBayDIT.ui.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//todo I need to query all users that verified=false
@RestController
@RequestMapping("admin")
public class AdminController {


    //todo getUsers that are not verified
    //todo getDetails of user
    //todo post /{id} user to verify him


//    @GetMapping("/userlist")
//    public ResponseEntity<Object> userList() {
//
//        List<UserEntity> allUsers = userRepo.findByRoleIsNotNull();
//        UserListOutputModel output = new UserListOutputModel();
//        for (UserEntity u : allUsers) {
//            try {
//                output.addUser(userEntityService.getUserOutputModelFromUser(u));
//            } catch (IOException e) {
//                return new ResponseEntity<>("Could not load images", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//        return new ResponseEntity<>(output, HttpStatus.OK);
//    }
//


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
