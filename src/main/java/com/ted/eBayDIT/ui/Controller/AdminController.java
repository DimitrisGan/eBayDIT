package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.response.AdminRest;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import com.ted.eBayDIT.ui.model.response.UserDetailsResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    ItemService itemService;



//    @RequestMapping(value="/findByID", method=RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
//    public @ResponseBody MyXmlAnnotatedObject findById(@Param("id") BigInteger id) {
//
//        return someRepository.findById(id);
//    }

    @GetMapping(path = "/download_auctions", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> downloadAuction(){

//        UserDto userDto = userService.getUserByUserId(id);
//        ModelMapper modelMapper = new ModelMapper();
//        returnValue = modelMapper.map(userDto, UserRest.class);

        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ItemDto> items = itemService.allItems();

        for (ItemDto itemDto : items) {
            AuctionsResponseModel auction =  modelMapper.map(itemDto, AuctionsResponseModel.class);
            auctionsRespList.add(auction);

        }

        return new ResponseEntity<>(auctionsRespList,HttpStatus.OK);

    }


    @GetMapping("/not_verified_users") //ex : /userlist
    public ResponseEntity<Object> getNotVerifiedUsersList(/*@RequestParam(value = "page",defaultValue = "0") int page,
                                                          @RequestParam(value = "limit",defaultValue = "2") int limit,
                                                          @RequestParam(value = "orderBy",defaultValue = "username") int sortBy*/) {

        List<UserDetailsResponseModel> returnUsersList = new ArrayList<>();

        List<UserDto> allNotVerifiedUsersList = userService.getAllNotVerifiedUsers();


        ModelMapper modelMapper = new ModelMapper();
        for (UserDto userDto : allNotVerifiedUsersList) {

            UserDetailsResponseModel returnUser = modelMapper.map(userDto, UserDetailsResponseModel.class);

            returnUsersList.add(returnUser);
        }

        return new ResponseEntity<>(returnUsersList, HttpStatus.OK);

    }

    @GetMapping(path ="/users") //ex : /allUsers
    public ResponseEntity<Object> getUsers(@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
                                           @RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
                                           @RequestParam(value = "orderBy",defaultValue = "username") String sortBy,
                                           @RequestParam(value = "order",defaultValue = "asc") String orderType) { //asc or desc


        List<UserDto> list = userService.getAllUsersFiltered(pageNo, pageSize, sortBy, orderType);

        List<UserDetailsResponseModel> returnUsersList =new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        for (UserDto userDto : list) {

            UserDetailsResponseModel returnUser = modelMapper.map(userDto, UserDetailsResponseModel.class);
            returnUsersList.add(returnUser);
        }

        AdminRest adminRest= new AdminRest();
        adminRest.setUsers(returnUsersList);
        adminRest.setTotalPages(list.get(0).getTotalPages());
        adminRest.setTotalUsers(this.userService.usersNumber());

        return new ResponseEntity<>(adminRest, HttpStatus.OK);

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
        UserDetailsResponseModel returnValue = modelMapper.map(userDto, UserDetailsResponseModel.class);


        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }



    //todo exportInXML()
}
