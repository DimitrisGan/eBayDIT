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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

//    @Override
//    public List<ItemDto> getFilteredAuctions(int pageNo, int pageSize, String sortBy, String orderType) {
//
//
//
//        //todo sortbyCategoy
//        //todo sortbyDescription  ---> str1.toLowerCase().contains(str2.toLowerCase())
//        //todo sortByPrice
//        //todo sortByLocation
//        //todo tha emfanizontai se paging ta auctions paging
//
//
//
//
//        return null;
//    }


    @Override
    public List<ItemDto> getFilteredAuctions(int pageNo, int pageSize, String sortBy, String sortType) {

//        if(pageNo>0) pageNo = pageNo-1; //to not get confused wit zero page

        List<ItemDto> returnValue = new ArrayList<>();
        Pageable paging;

        if (sortType.equals("asc"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<ItemEntity> pagedResult = itemRepo.findAll(paging);
        int totalPages = pagedResult.getTotalPages();


        List<ItemEntity> items = pagedResult.getContent();

        ModelMapper modelMapper = new ModelMapper();
        for (ItemEntity itemEntity: items){
            ItemDto itemDto = modelMapper.map(itemEntity, ItemDto.class);
//            itemDto.setTotalPages(totalPages);
            returnValue.add(itemDto);
        }

        return returnValue;

    }

    @Override
    public List<ItemDto> filterAuctions(List<String> categoryNameList, String description, String locationText, BigDecimal lowestPrice, BigDecimal highestPrice) {
        List<ItemDto> returnValue=new ArrayList<>();

        List<ItemEntity> items = itemRepo.findAll();

        for (ItemEntity item : items) {

            if (! categoryNameList.isEmpty()){


            }

            if (description.isEmpty()){

            }

            if ( locationText.isEmpty()){

            }







        }




        return returnValue;
    }


}
