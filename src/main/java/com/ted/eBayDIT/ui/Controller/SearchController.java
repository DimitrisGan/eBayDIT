package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.service.SearchService;
import com.ted.eBayDIT.ui.model.response.AuctionsFilteredSearchResponseModel;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    //todo searchByCategoryEndpoint
    //todo searchActiveAuctions
    //todo searchByDescription

    @Autowired
    SearchService searchService;


    @GetMapping(path ="/auctions/active")
    public ResponseEntity<Object> getActiveAuctions() throws ParseException {
        AuctionsResponseModel auctionsResp = new AuctionsResponseModel();

        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();

        List<ItemDto> auctionsList = searchService.getActiveAuctions();

        ModelMapper modelMapper = new ModelMapper();
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

                                                         @RequestParam(value = "categories", required = false) String categories,
                                                         @RequestParam(value = "description", required = false) String description,
                                                         @RequestParam(value = "location", required = false) String locationText,

                                                         @RequestParam(value = "lowestPrice", required = false) BigDecimal lowestPrice,
                                                         @RequestParam(value = "highestPrice", required = false ) BigDecimal highestPrice

                                                                        ) {



        AuctionsFilteredSearchResponseModel auctionsFilterResp = new AuctionsFilteredSearchResponseModel();
        List<AuctionsResponseModel> auctionsRespList  = new ArrayList<>();


        List<String> categoryNameList=new ArrayList<>();
        if (categories != null) {
            categoryNameList = Arrays.asList(categories.split("\\s*,\\s*"));
        }

        /*get the itemIds*/
        List<Long> filteredList = searchService.filterAuctions(categoryNameList ,description,locationText, lowestPrice ,highestPrice); //filter and discard not needed auction

        /*do the pagination and sorting for the given auction-item ids*/
        List<ItemDto> auctionsList = searchService.getPaginatedFilteredAuctions(pageNo, pageSize, sortBy, orderType, filteredList);


        ModelMapper modelMapper = new ModelMapper();
        AuctionsResponseModel auctionResp  = new AuctionsResponseModel();
        for (ItemDto itemDto : auctionsList) {
            auctionResp = modelMapper.map(itemDto, AuctionsResponseModel.class);
            auctionsRespList.add(auctionResp);
        }


        auctionsFilterResp.setTotalFilteredAuctions(auctionsList.size());
        auctionsFilterResp.setAuctions(auctionsRespList);

        return new ResponseEntity<>(auctionsFilterResp, HttpStatus.OK);

    }


//    public ResponseEntity<Object> getUsers(@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
//                                           @RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
//                                           @RequestParam(value = "orderBy",defaultValue = "username") String sortBy,
//                                           @RequestParam(value = "order",defaultValue = "asc") String orderType) { //asc or desc
//
//
//        List<UserDto> list = userService.getAllUsers(pageNo, pageSize, sortBy, orderType);
//
//        List<UserRest> returnUsersList =new ArrayList<>();
//
//        ModelMapper modelMapper = new ModelMapper();
//        for (UserDto userDto : list) {
//
//            UserRest returnUser = modelMapper.map(userDto, UserRest.class);
//            returnUsersList.add(returnUser);
//        }
//
//        AdminRest adminRest= new AdminRest();
//        adminRest.setUsers(returnUsersList);
//        adminRest.setTotalPages(list.get(0).getTotalPages());
//        adminRest.setTotalUsers(this.userService.usersNumber());
//
//        return new ResponseEntity<>(adminRest, HttpStatus.OK);
//
//    }



}
