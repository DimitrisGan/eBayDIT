package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.exception.UserServiceException;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserChangePasswordRequest;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.UsernameExistsRequestModel;
import com.ted.eBayDIT.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// mou vgazei same origin provlima
// Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://localhost:8080/api/register.
// (Reason: CORS header ‘Access-Control-Allow-Origin’ missing).
//
// opote evala auto, an thes allakse to apla tsekare pws na to vgaloume
// alla na min exoume tripes sto security
//@CrossOrigin
//http://localhost:8080/register [previous /users]


@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @GetMapping(path ="users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){

        String currUserId = securityService.getCurrentUser().getUserId();
        if (!(currUserId.equals(id))){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = userService.getUserByUserId(id) ;

//        BeanUtils.copyProperties(userDto,returnUser);
        ModelMapper modelMapper = new ModelMapper();
        UserRest returnUser = modelMapper.map(userDto, UserRest.class);

        return   new ResponseEntity<>(returnUser, HttpStatus.OK);

    }







    @PostMapping("register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserRest returnValue =new UserRest();

//        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());


        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        userService.createUser(userDto);



//        BeanUtils.copyProperties(createdUser,returnValue);

//        if (!Validator.validateEmail(input.getEmail())) {
//            return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
//        }
//        if (input.getPassword() == null || input.getPassword().equals("")) {
//            String msg = "Can't register with empty password";
//            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//        }
//        if (input.getName() == null || input.getName().equals("")
//                || input.getSurname() == null || input.getSurname().equals("")) {
//            String msg = "Can't register with empty name or surname";
//            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//        }
//        if (userService.findByEmail(input.getEmail()) != null) {
//            String msg = "A user with this email is already registered";
//            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
//        }
//        try {
//            UserEntity user = new UserEntity.UserBuilder(input.getEmail(), input.getPassword())
//                    .name(input.getName())
//                    .surname(input.getSurname())
//                    .telNumber(input.getTelNumber())
//                    .picture(sm.storeFile(input.getPicture())).build();
//            userService.save(user);
//            return new ResponseEntity<>(userEntityService.getUserOutputModelFromUser(user)
//                    , HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Couldn't save picture", HttpStatus.INTERNAL_SERVER_ERROR);
//        }


        return   new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PostMapping(path = "/exists")
    public UsernameExistsRest usernameExists(@RequestBody UsernameExistsRequestModel username){

        UsernameExistsRest returnValue = new UsernameExistsRest();
        boolean exists = userService.userExists(username.getUsername());
        returnValue.setExists(exists);
        return returnValue;
    }


    @PutMapping(path ="users/change_password")
    public ResponseEntity<Object> updateUsersPassword(@RequestBody UserChangePasswordRequest userPassRequestModel){

        UserDto currentUser = securityService.getCurrentUser();

        if (! this.userService.isPasswordEqual(currentUser.getUserId() , userPassRequestModel.getCurrPassword()) ) //if passwords are not equal return forbidden status code
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserDto updatedUser =  this.userService.updatePassword(currentUser.getUserId() , userPassRequestModel.getNewPassword());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }



    @PutMapping(path ="users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails){

        UserDto currentUser = securityService.getCurrentUser();

        if (! currentUser.getUserId().equals(id)){
            String msg = "Not authorized to do that";
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }


//        UserRest returnValue =new UserRest();

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto updatedUser = userService.updateUser(id,userDto);

//        BeanUtils.copyProperties(updatedUser,returnValue);

        return new ResponseEntity<>(/*returnValue, */HttpStatus.OK);
    }

    @DeleteMapping(path ="users/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue =  new OperationStatusModel();

        this.userService.deleteUser(id);

        returnValue.setOperationName("DELETE");
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }


}
