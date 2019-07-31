package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// mou vgazei same origin provlima
// Cross-Origin Request Blocked: The Same Origin Policy disallows reading the remote resource at http://localhost:8080/api/register.
// (Reason: CORS header ‘Access-Control-Allow-Origin’ missing).
//
// opote evala auto, an thes allakse to apla tsekare pws na to vgaloume
// alla na min exoume tripes sto security
//@CrossOrigin
@RestController
@RequestMapping/*("users")*/  //http://localhost:8080/register [previous /users]
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

    @PostMapping("register")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue =new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);


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

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }


}
