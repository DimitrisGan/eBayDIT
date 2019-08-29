package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.SellerDetailsEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private ItemService itemService;




    @Override
    public List<ItemDto> getActiveAuctions() throws ParseException {

        List<ItemEntity> auctions_list = itemRepo.findByEventStartedTrueAndEventFinishedFalse();
        List<ItemDto> returnList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();


        for (ItemEntity itemEntity : auctions_list) {


            if (itemService.isAuctionFinishedByTime(itemEntity.getItemID()) )
                continue;

            ItemDto itemDto =  modelMapper.map(itemEntity, ItemDto.class);
            returnList.add(itemDto);

        }

        return returnList;
    }







}
