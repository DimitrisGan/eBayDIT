package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.ui.model.request.AuctionDetailsRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions")
public class AuctionController {


    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;


    @Autowired
    ItemService itemService;


    @PostMapping(/*path ="/create"*/)
    public ResponseEntity<Object> createAuction(@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        UserDto currentUser = securityService.getCurrentUser();

        ModelMapper modelMapper = new ModelMapper();
        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class); //todo first step here


        this.itemService.addNewItem(itemDto); //create item-auction


        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(path ="/{id}") //add new bid for example
    public ResponseEntity<Object> updateAuction(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping(path ="/{id}") //add new bid for example
    public ResponseEntity<Object> getAuctionInfo(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.CREATED);

    }



}
