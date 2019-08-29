package com.ted.eBayDIT.ui.Controller;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.service.SearchService;
import com.ted.eBayDIT.ui.model.response.AuctionsResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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





}
