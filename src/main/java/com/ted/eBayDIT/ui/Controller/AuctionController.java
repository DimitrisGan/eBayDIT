package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions")
public class AuctionController {


//    @Autowired
//    UserService userService;
//
//    @Autowired
//    SecurityService securityService;


//    @Autowired
//    ItemService itemService;
//
//    @GetMapping(path ="users/{id}")
//    public UserRest getUser(@PathVariable String id){
//
//        UserRest returnUser =new UserRest();
//
//        UserDto userDto = userService.getUserByUserId(id) ;
//        BeanUtils.copyProperties(userDto,returnUser);
//        return returnUser;
//    }


}
