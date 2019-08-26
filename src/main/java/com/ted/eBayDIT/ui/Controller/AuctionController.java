package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.ui.model.request.AddBidAuctionRequestModel;
import com.ted.eBayDIT.ui.model.request.AuctionDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.CreateAuctionRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(/*"/auctions"*/)
public class AuctionController {


    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;


    @Autowired
    ItemService itemService;


    @PostMapping(path ="/auctions")
    public ResponseEntity<Object> createAuction(@RequestBody CreateAuctionRequestModel createAuctionRequestModel){

        UserDto currentUser = securityService.getCurrentUser();

        ModelMapper modelMapper = new ModelMapper();
        ItemDto itemDto = modelMapper.map(createAuctionRequestModel, ItemDto.class); //todo first step here


        this.itemService.addNewItem(itemDto); //create item-auction


        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> updateAuction(@PathVariable String auctionId,@RequestBody AddBidAuctionRequestModel newBid){

        //todo findAuctionById(auctionID)

        //todo send back exception if startedEvent == true
        //todo this.itemService.updateAuction(id);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path ="/start_auction/{id}") //add new bid for example
    public ResponseEntity<Object> startAuction(@PathVariable Long id){//id : auctionId

        //todo findAuctionById(auctionID)

        //todo check if currUser owns the auction
        if (! itemService.userOwnsTheAuction(id)){
            String msg = "Not authorized to change auction that you don't own!";
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }

        itemService.startAuction(id);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> getAuctionInfo(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping(path ="/auctions/{id}")
    public ResponseEntity<Object> deleteAuction(@PathVariable String id){

    //todo this.itemService.deleteAuction(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }





}
