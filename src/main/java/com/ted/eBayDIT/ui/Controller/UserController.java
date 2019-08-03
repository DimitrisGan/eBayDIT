package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.exception.UserServiceException;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.UsernameExistsRequestModel;
import com.ted.eBayDIT.ui.model.response.*;
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
    public UserRest getUser(@PathVariable String id){

        UserRest returnUser =new UserRest();

        UserDto userDto = userService.getUserByUserId(id) ;
        BeanUtils.copyProperties(userDto,returnUser);
        return returnUser;
    }

    @GetMapping(path ="users")
    public List<UserRest> getUsers(){

        List<UserRest> returnUsersList =new ArrayList<>();

        List<UserDto> usersList = userService.getUsers() ;

        for (UserDto userDto : usersList) {
            UserRest userModel  = new UserRest();
            BeanUtils.copyProperties(userDto,userModel);
            returnUsersList.add(userModel);
        }


        return returnUsersList;
    }





    @PostMapping("register")
    public /*todo ResponseEntity<Object>*/ UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) throws Exception {

        UserRest returnValue =new UserRest();

        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        //todo add exception also for the other fields

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);





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


        return returnValue;
    }



    @PostMapping(path = "/exists")
    public UsernameExistsRest usernameExists(@RequestBody UsernameExistsRequestModel username){

        UsernameExistsRest returnValue = new UsernameExistsRest();
        boolean exists = userService.userExists(username.getUsername());
        returnValue.setExists(exists);
        return returnValue;
    }


    @PutMapping(path ="users/{id}")
    public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails){

        UserDto currentUser = securityService.getCurrentUser(); //todo check if working

        UserRest returnValue =new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto updatedUser = userService.updateUser(id,userDto);

        BeanUtils.copyProperties(updatedUser,returnValue);




        return returnValue;
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
