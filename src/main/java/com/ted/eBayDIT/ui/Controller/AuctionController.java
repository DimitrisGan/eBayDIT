package com.ted.eBayDIT.ui.Controller;


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
import com.ted.eBayDIT.ui.model.response.PhotoResponseModel;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(/*"/auctions"*/)
public class AuctionController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;


    @Autowired
    ItemService itemService;

    @Autowired
    PhotoService photoService;



    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);



    @PostMapping("auctions/{id}/upload_multiple_photos")
    public List<ResponseEntity<Object>> uploadMultipleFiles(@PathVariable Long id, @RequestParam(name="imageFile", required=false) MultipartFile[] imageFile) {
        return Arrays.stream(imageFile)
                .map(file -> uploadFile(id,file))
                .collect(Collectors.toList());

    }


    @PostMapping("auctions/{id}/upload_photo")
    public ResponseEntity<Object> uploadFile(@PathVariable Long id,@RequestParam(name="file", required=false) MultipartFile imageFile) {

        ItemDto itemDto = itemService.getItem(id); //create item-auction

        if (imageFile != null) {

            PhotoDto photoDto = photoService.preparePhoto(imageFile, itemDto);

            try {
                itemService.saveImage(imageFile, photoDto);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//        ModelMapper modelMapper = new ModelMapper();
//        AuctionsResponseModel returnVaule = modelMapper.map(createAuctionRequestModel, ItemDto.class);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping(path ="/auctions" )
    public ResponseEntity<Object> createAuction(@RequestBody CreateAuctionRequestModel createAuctionRequestModel)
            throws ParseException {

        ModelMapper modelMapper = new ModelMapper();
        ItemDto itemDto = modelMapper.map(createAuctionRequestModel, ItemDto.class);

        ItemDto newlyCreatedItemDto = itemService.addNewItem(itemDto); //create item-auction

        return new ResponseEntity<>(newlyCreatedItemDto,HttpStatus.CREATED);

    }


    @DeleteMapping(path ="/auctions/delete_photo/{photoId}" )
    public ResponseEntity<Object> deletePhoto(@PathVariable int photoId){

        this.photoService.deletePhoto(photoId);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PutMapping(path ="/auctions/{id}") //add new bid for example
    public ResponseEntity<Object> editAuction(@PathVariable Long id,@RequestBody AuctionDetailsRequestModel editAuctionDetails){

        //check first if item/auction exists!
        if (! itemService.itemExists(id)){
            String msg = "Auction doesn't exist to edit!";
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }


//        try {
////todo            this.itemService.editAuction(id, );
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }


        //todo findAuctionById(auctionID)

        //todo send back exception if startedEvent == true

        return new ResponseEntity<>(HttpStatus.CREATED);

    }






    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = photoService.loadFileAsResource(fileName);



        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
            if (auctionsResp.getPhotos().isEmpty()){

                //add default photo
                PhotoDto defaultPhotoDto = photoService.loadDefaultItemImage();
                PhotoResponseModel defaultPhotoResp = modelMapper.map(defaultPhotoDto, PhotoResponseModel.class);
                auctionsResp.getPhotos().add(defaultPhotoResp);
            }
            auctionsRespList.add(auctionsResp);
        }

        return new ResponseEntity<>(auctionsRespList,HttpStatus.OK);

    }




    @GetMapping(path ="/auctions/{id}")
    public ResponseEntity<Object> getAuctionInfo(@PathVariable String auctionId,@RequestBody AuctionDetailsRequestModel auctionDetailsRequestModel){

        //todo findAuctionById(auctionID)

        return new ResponseEntity<>(HttpStatus.OK);

    }




}
