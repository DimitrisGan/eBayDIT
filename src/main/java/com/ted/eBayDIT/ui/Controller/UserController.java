package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.UsernameExistsRequestModel;
import com.ted.eBayDIT.ui.model.response.OperationStatusModel;
import com.ted.eBayDIT.ui.model.response.RequestOperationStatus;
import com.ted.eBayDIT.ui.model.response.UserRest;
import com.ted.eBayDIT.ui.model.response.UsernameExistsRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue =new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);


        return returnValue;
    }



    @GetMapping(path = "register")
    public UsernameExistsRest usernameExists(@RequestBody UsernameExistsRequestModel username){

        UsernameExistsRest returnValue = new UsernameExistsRest();
        boolean exists = userService.userExists(username.getUsername());
        returnValue.setExists(exists);
        return returnValue;
    }


    @PutMapping(path ="users/{id}")
    public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails){

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
