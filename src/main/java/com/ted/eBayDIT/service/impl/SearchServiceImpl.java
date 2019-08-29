package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.SellerDetailsEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private ItemRepository itemRepo;

//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private Utils utils;
//
//    @Autowired
//    private CategoryRepository categRepo;
//
//    @Autowired
//    SecurityService securityService;
//
//    @Autowired
//    SellerDetailsRepository sellerRepo;
//
//    @Autowired
//    BidderDetailsRepository bidderRepo;
//
//
//    @Autowired
//    ItemLocationRepo itemLocationRepo;
//


    @Override
    public List<ItemDto> getActiveAuctions() {
        List<ItemDto> returnList = new ArrayList<>();

        List<ItemEntity> auctions_list = itemRepo.findByEventStartedTrue();

        ModelMapper modelMapper = new ModelMapper();

        /*convert items/auctions List from Entity to Dto datatype*/
        for (ItemEntity auctionEntity : auctions_list) {
            ItemDto itemDto =  modelMapper.map(auctionEntity, ItemDto.class);
            returnList.add(itemDto);
        }

        return returnList;
    }







}
