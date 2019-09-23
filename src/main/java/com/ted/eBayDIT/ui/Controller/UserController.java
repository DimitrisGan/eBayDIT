package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserChangePasswordRequestModel;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.UsernameExistsRequestModel;
import com.ted.eBayDIT.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            return   new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserDto userDto = userService.getUserByUserId(id) ;

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDetailsResponseModel returnUser = modelMapper.map(userDto, UserDetailsResponseModel.class);

        return   new ResponseEntity<>(returnUser, HttpStatus.OK);

    }


    @PostMapping("register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserDetailsResponseModel returnValue =new UserDetailsResponseModel();

//        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        userService.createUser(userDto);

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
    public ResponseEntity<Object> updateUsersPassword(@RequestBody UserChangePasswordRequestModel userPassRequestModel){

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

//        UserDetailsResponseModel returnValue =new UserDetailsResponseModel();

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto updatedUser = userService.updateUser(id,userDto);

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
