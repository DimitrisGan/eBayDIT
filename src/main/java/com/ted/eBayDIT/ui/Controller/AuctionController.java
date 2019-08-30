package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.BidDto;
import com.ted.eBayDIT.dto.BidderDto;
import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.PhotoService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.ui.model.request.AddBidAuctionRequestModel;
import com.ted.eBayDIT.ui.model.request.AuctionDetailsRequestModel;
import com.ted.eBayDIT.ui.model.request.CreateAuctionRequestModel;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
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
    public ResponseEntity<Object> createAuction(@RequestParam("imageFile") MultipartFile imageFile,@RequestBody CreateAuctionRequestModel createAuctionRequestModel) throws ParseException {

        ModelMapper modelMapper = new ModelMapper();
        ItemDto itemDto = modelMapper.map(createAuctionRequestModel, ItemDto.class);

        //check if ends Date is after Current date
//        if (Utils.convertStringDateToDateDataType(itemDto.getEnds()).before(Utils.getCurrentDate())){
//            String msg = "Auction doesn't exist to start!";
//            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
//        }


        this.itemService.addNewItem(itemDto); //create item-auction




        PhotoDto photoDto = new PhotoDto();
        photoDto.setFileName(imageFile.getOriginalFilename());
        photoDto.setPath("/photo/");
        photoDto.setItem(itemDto);
        try {
            itemService.saveImage(imageFile, photoDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping(path ="/auctions/buyout/{auctionId}") //add new bid for example

    public ResponseEntity<Object> buyItemInAuction(@PathVariable Long auctionId,@RequestBody AddBidAuctionRequestModel newBidRequest) throws ParseException {

        //check first if item/auction exists!
        if (! itemService.itemExists(auctionId)){
            String msg = "Auction doesn't exist to buyout!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //check if auction is started!
        if (! itemService.auctionStarted(auctionId)){
            String msg = "Auction hasn't started to auctionId!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //check if auction is finished!
        if (! itemService.auctionStarted(auctionId)){
            String msg = "Auction hasn't started to auctionId!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


        //the check for if auction has finished is done inside the itemService.addBid()
        itemService.buyout(auctionId);
                //addBid(auctionId,newBidRequest.getAmount(),newBidRequest.getBidderId());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path ="/auctions/add_bid/{id}") //add new bid for example
    public ResponseEntity<Object> addBidInAuction(@PathVariable Long id,@RequestBody AddBidAuctionRequestModel newBidRequest) throws ParseException {

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

        //check if auction is finished!
        if (! itemService.auctionStarted(id)){
            String msg = "Auction hasn't started to make bids!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


        //the check for if auction has finished is done inside the itemService.addBid()
        itemService.addBid(id,newBidRequest.getAmount(),newBidRequest.getBidderId());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> editAuction(@PathVariable Long id,@RequestBody AuctionDetailsRequestModel editAuctionDetails){

        //check first if item/auction exists!
        if (! itemService.itemExists(id)){
            String msg = "Auction doesn't exist to edit!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }



        //todo findAuctionById(auctionID)

        //todo send back exception if startedEvent == true
        //todo this.itemService.updateAuction(id);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PutMapping(path ="/auctions/start/{id}") //add new bid for example
    public ResponseEntity<Object> startAuction(@PathVariable Long id) throws ParseException {//id : auctionId


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





    @DeleteMapping(path ="/auctions/{id}")
    public ResponseEntity<Object> deleteAuction(@PathVariable Long id) throws ParseException {
        //check if currUser owns the auction!
        if (! itemService.userOwnsTheAuction(id)){
            String msg = "Not authorized to delete auction that you don't own!";
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }
        int  i = this.itemService.deleteAuction(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }



//    -------------------- UNDER CONSTRUCTON -----------------------------

    //todo search GET check!!!!!!!!!!!!!!!!!!

    //todo param
    @GetMapping(path ="/auctions")
    public ResponseEntity<Object> getAllAuctions() throws ParseException {

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





    @GetMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> getAuctionInfo(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.OK);

    }



    @PostMapping("/auctions/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
            String returnValue = "start";

            PhotoDto photoDtO = new PhotoDto();
            photoDtO.setFileName(imageFile.getOriginalFilename());
            photoDtO.setPath("/photo/");

            try {
                itemService.saveImage(imageFile, photoDtO);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//                log.error("Error saving photo", e);
                returnValue = "error";
            }

            return returnValue;
        }


}
