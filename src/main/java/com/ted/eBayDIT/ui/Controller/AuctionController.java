package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.BidDto;
import com.ted.eBayDIT.dto.BidderDto;
import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.ui.model.request.AddBidAuctionRequestModel;
import com.ted.eBayDIT.ui.model.request.AuctionDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.CreateAuctionRequestModel;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        ModelMapper modelMapper = new ModelMapper();
        ItemDto itemDto = modelMapper.map(createAuctionRequestModel, ItemDto.class); //todo first step here

        this.itemService.addNewItem(itemDto); //create item-auction

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping(path ="/auctions/add_bid/{id}") //add new bid for example
    public ResponseEntity<Object> addBidInAuction(@PathVariable Long id,@RequestBody AddBidAuctionRequestModel newBidRequest){

        //check first if item/auction exists!
        if (! itemService.itemExists(id)){
            String msg = "Auction doesn't exist to start!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //check if auction is started!
        if (! itemService.auctionStarted(id)){
            String msg = "Auction hasn't started to make bids!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //todo # check if currentTime < Ends Time
        //check if auction is finished!
        if (itemService.auctionFinished(id)){
            String msg = "Auction has finished!Thus, cannot bid!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


//        ModelMapper modelMapper = new ModelMapper();
//        BidDto bidDto = modelMapper.map(newBidRequest, BidDto.class);
//        BidDto bidDto = new BidDto();
//        BidderDto bidderDto = new BidderDto();



        itemService.addBid(id,newBidRequest.getAmount(),newBidRequest.getBidderId());



        //todo findAuctionById(auctionID)



        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> editAuction(@PathVariable Long id,@RequestBody AuctionDetailsRequestModel editAuctionDetails){

        //check first if item/auction exists!
        if (! itemService.itemExists(id)){
            String msg = "Auction doesn't exist to edit!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //check if auction is started!
//        if (itemService.auctionStarted(id)){
//            String msg = "Auction has started!Thus, you cannot edit it!";
//            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//        }

        //todo # check if currentTime < Ends Time
        //check if auction is finished!
        if (itemService.auctionFinished(id)){
            String msg = "Auction has finished!Thus, cannot edit!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


        //todo findAuctionById(auctionID)

        //todo send back exception if startedEvent == true
        //todo this.itemService.updateAuction(id);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping(path ="/auctions/start/{id}") //add new bid for example
    public ResponseEntity<Object> startAuction(@PathVariable Long id){//id : auctionId


        //check first if item/auction exists to start it!
        if (! itemService.itemExists(id)){
            String msg = "Auction doesn't exist to start!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        //check if currUser owns the auction!
        if (! itemService.userOwnsTheAuction(id)){
            String msg = "Not authorized to change auction that you don't own!";
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }
        //check first if item/auction has already started!
        if ( itemService.auctionStarted(id)){
            String msg = "Auction has already started!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


        itemService.startAuction(id);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping(path ="/auctions")
    public ResponseEntity<Object> getAllAuctions(){

        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();

        List<ItemDto> auctionsList = itemService.getAllUserAuctions();

        AuctionsResponseModel auctionsResp = new AuctionsResponseModel();

        ModelMapper modelMapper = new ModelMapper();
        for (ItemDto itemDto : auctionsList) {
            auctionsResp = modelMapper.map(itemDto, AuctionsResponseModel.class);
            auctionsRespList.add(auctionsResp);
        }

        return new ResponseEntity<>(auctionsRespList,HttpStatus.OK);

    }


    @DeleteMapping(path ="/auctions/{id}")
    public ResponseEntity<Object> deleteAuction(@PathVariable Long id){
        //check if currUser owns the auction!
        if (! itemService.userOwnsTheAuction(id)){
            String msg = "Not authorized to delete auction that you don't own!";
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }
        int  i = this.itemService.deleteAuction(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }



//    -------------------- UNDER CONSTRUCTON -----------------------------

    @GetMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> getAuctionInfo(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.OK);

    }



}
