package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.*;
import com.ted.eBayDIT.ui.model.response.AuctionsFilteredSearchResponseModel;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import com.ted.eBayDIT.ui.model.response.PhotoResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    //todo searchByCategoryEndpoint
    //todo searchActiveAuctions
    //todo searchByDescription

    @Autowired
    SearchService searchService;

    @Autowired
    PhotoService photoService;

    @Autowired
    ItemService itemService;

    @Autowired
    RecommendService recommendService;

    @Autowired
    SecurityService securityService;

    @Autowired
    VisitService visitService;




    @PostMapping(path ="/auctions/start_lsh")
    public ResponseEntity<Object> startLsh()  {


        recommendService.createLsh();

        return new ResponseEntity<>(HttpStatus.OK);

    }



    @GetMapping(path ="/auctions/recommend_auctions")
    public ResponseEntity<Object> getRecommendedAuctions()  {

        AuctionsResponseModel auctionResp = new AuctionsResponseModel();
        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();

        List<Long> recommendedAuctionIdsList = new ArrayList<>();


        UserDto currUser = securityService.getCurrentUser();

        if (currUser != null){
            recommendedAuctionIdsList = recommendService.getRecommendedAuctionIdsForUser(); //recommend for current user

        }else{
            recommendedAuctionIdsList = recommendService.getRecommendedAuctionIdsForGuest(); //recommend for current guest
        }



        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (Long auctionId : recommendedAuctionIdsList) {
            ItemDto itemDto2recommend = itemService.getItem(auctionId);

            auctionResp = modelMapper.map(itemDto2recommend, AuctionsResponseModel.class);

            if (auctionResp.getPhotos().isEmpty()){
                PhotoResponseModel defaultPhotoResp = photoService.addDefaultPhotoIfNoPhotosExist();
                auctionResp.setDefaultPhoto(defaultPhotoResp);
            }
            

            auctionsRespList.add(auctionResp);

        }

        return new ResponseEntity<>(auctionsRespList, HttpStatus.OK);

    }



    @GetMapping(path ="/auctions/active")
    public ResponseEntity<Object> getActiveAuctions() throws ParseException {
        AuctionsResponseModel auctionsResp = new AuctionsResponseModel();

        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();

        List<ItemDto> auctionsList = searchService.getActiveAuctions();

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (ItemDto itemDto : auctionsList) {
            auctionsResp = modelMapper.map(itemDto, AuctionsResponseModel.class);
            auctionsRespList.add(auctionsResp);
        }

        return new ResponseEntity<>(auctionsRespList, HttpStatus.OK);

    }


//    public String controllerMethod(@RequestParam(value="myParam[]") String[] myParams)


    @GetMapping(path ="/auctions/filters")
    public ResponseEntity<Object> getAllAuctionsByFilter(@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
                                                         @RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
                                                         @RequestParam(value = "orderBy",defaultValue = "name") String sortBy,
                                                         @RequestParam(value = "order",defaultValue = "asc") String orderType,

                                                         @RequestParam(value = "categories", required = false) List<String> categories,
                                                         @RequestParam(value = "description", required = false) String description,
                                                         @RequestParam(value = "location", required = false) String locationText,

                                                         @RequestParam(value = "lowestPrice", required = false) BigDecimal lowestPrice,
                                                         @RequestParam(value = "highestPrice", required = false ) BigDecimal highestPrice

                                                                        ) {



        AuctionsFilteredSearchResponseModel auctionsFilterResp = new AuctionsFilteredSearchResponseModel();
        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();

        List<String> categoryNameList=new ArrayList<>();
        if (categories != null) {
            categoryNameList = categories;
        }
        if (description == null){
            description = "";
        }
        if (locationText == null){
            locationText = "";
        }

        /*get the itemIds*/
        List<Long> filteredList = searchService.filterAuctions(categoryNameList ,description,locationText, lowestPrice ,highestPrice); //filter and discard not needed auction

        /*do the pagination and sorting for the given auction-item ids*/
        List<ItemDto> auctionsList = searchService.getPaginatedFilteredAuctions(pageNo, pageSize, sortBy, orderType, filteredList);


        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        AuctionsResponseModel auctionResp  = new AuctionsResponseModel();
        for (ItemDto itemDto : auctionsList) {
            auctionResp = modelMapper.map(itemDto, AuctionsResponseModel.class);
            if (auctionResp.getPhotos().isEmpty()){
               PhotoResponseModel defaultPhotoResp = photoService.addDefaultPhotoIfNoPhotosExist();
               auctionResp.setDefaultPhoto(defaultPhotoResp);
            }

            auctionsRespList.add(auctionResp);

        }

        auctionsFilterResp.setTotalFilteredAuctions(filteredList.size());
        auctionsFilterResp.setAuctions(auctionsRespList);

        return new ResponseEntity<>(auctionsFilterResp, HttpStatus.OK);

    }



    @GetMapping(path ="/auctions/{id}")
    public ResponseEntity<Object> getAuctionInfo(@PathVariable Long id){  //id = auctionId
        AuctionsResponseModel auctionResp = new AuctionsResponseModel();

        UserDto currUser = securityService.getCurrentUser();

        if (currUser != null){
            visitService.addVisit(currUser,id);
        }

        ItemDto itemDto = this.itemService.getItem(id);

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        auctionResp = modelMapper.map(itemDto, AuctionsResponseModel.class);
        if (auctionResp.getPhotos().isEmpty()){
            //add default photo
            PhotoResponseModel defaultPhotoResp =   photoService.addDefaultPhotoIfNoPhotosExist();
            auctionResp.setDefaultPhoto(defaultPhotoResp);
        }

        return new ResponseEntity<>(auctionResp,HttpStatus.OK);

    }




}
