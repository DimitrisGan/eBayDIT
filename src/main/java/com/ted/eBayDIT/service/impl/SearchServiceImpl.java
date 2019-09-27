package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.entity.CategoryEntity;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.repository.CategoryRepository;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.SearchService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    @Autowired
    private CategoryRepository categRepo;


    @Override
    public List<ItemDto> getActiveAuctions() throws ParseException {

        List<ItemEntity> auctions_list = itemRepo.findByEventStartedTrueAndEventFinishedFalse();
        List<ItemDto> returnList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        for (ItemEntity itemEntity : auctions_list) {


            if (itemService.isAuctionFinishedByTime(itemEntity.getItemID()))
                continue;

            ItemDto itemDto = modelMapper.map(itemEntity, ItemDto.class);
            returnList.add(itemDto);

        }

        return returnList;
    }



    @Override
    public List<ItemDto> getPaginatedFilteredAuctions(int pageNo, int pageSize, String sortBy, String sortType, List<Long> itemIdsList) {

//        if(pageNo>0) pageNo = pageNo-1; //to not get confused wit zero page

        List<ItemDto> returnValue = new ArrayList<>();
        Pageable paging;

        if (sortType.equals("asc"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<ItemEntity> pagedResult = itemRepo.findByItemIDIn(itemIdsList,paging);

        List<ItemEntity> items = pagedResult.getContent();

        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        for (ItemEntity itemEntity : items) {
            ItemDto itemDto = modelMapper.map(itemEntity, ItemDto.class);

            returnValue.add(itemDto);
        }

        return returnValue;

    }


    private List<ItemEntity> doDescriptionFilter(String description, List<ItemEntity> items) {
        List<ItemEntity> returnValue = new ArrayList<>();

        if (description.isEmpty()) {
            return items;
        }
        for (ItemEntity itemEntity : items) {
            if (itemEntity.getDescription() == null) //if item description is null continue
                continue;

            if (itemEntity.getDescription().toLowerCase().contains(description.toLowerCase()))
                returnValue.add(itemEntity);
        }

        return returnValue;
    }

    private List<ItemEntity> doLocationFilter(String locationText, List<ItemEntity> items) {
        List<ItemEntity> returnValue = new ArrayList<>();
        if (locationText.isEmpty())
            return items;

        for (ItemEntity itemEntity : items) {
            if (itemEntity.getLocation().getText().toLowerCase().contains(locationText.toLowerCase()))
                returnValue.add(itemEntity);
        }

        return returnValue;
    }

    private List<ItemEntity> doPriceFilter(BigDecimal lowestPrice, BigDecimal highestPrice, List<ItemEntity> items) {
        List<ItemEntity> returnValue = new ArrayList<>();

        if (lowestPrice == null && highestPrice == null) { // 0-0
            return items;
        }

        if (lowestPrice != null && highestPrice != null) { // 1-1

            for (ItemEntity itemEntity : items) {
                BigDecimal itemPrice = itemEntity.getCurrently();
                if (itemPrice.compareTo(lowestPrice) >= 0 &&  itemPrice.compareTo(highestPrice) <= 0  )
                    returnValue.add(itemEntity);
            }
        }
        if (lowestPrice!= null && highestPrice== null){ // 1-0

            for (ItemEntity itemEntity : items) {
                BigDecimal itemPrice = itemEntity.getCurrently();
                if (itemPrice.compareTo(lowestPrice) >= 0 )
                    returnValue.add(itemEntity);
            }
        }
        if (lowestPrice== null && highestPrice != null){ // 0-1

            for (ItemEntity itemEntity : items) {
                BigDecimal itemPrice = itemEntity.getCurrently();
                if (itemPrice.compareTo(highestPrice) <= 0  )
                    returnValue.add(itemEntity);
            }
        }


        return returnValue;
    }

    private List<ItemEntity> doCategoriesFilter(List<String> categoryNameList, List<ItemEntity> items) {
        List<ItemEntity> returnValue = new ArrayList<>();

        if (categoryNameList.isEmpty()) { //there is no category filter!Return the given list
            return items;
        }
        /*from here it means that filter with Categories has been added!*/

        int i=0;
        int prevCategId=-1; //root category

        List<CategoryEntity> CategoriesToSearchIfExistInItem = new ArrayList<>();
        CategoryEntity categ;

        for (String categStr : categoryNameList) {

            if (i==0) {
                categ = categRepo.findByName(categStr);
            }
            else {

                categ = categRepo.findByNameAndParentId(categStr, prevCategId);
            }

            CategoriesToSearchIfExistInItem.add(this.categRepo.findByNameAndParentId(categStr,categ.getParentId()));

            prevCategId = categ.getId();
            i++;
        }

        /*add only the items that contain all the categories*/
        for (ItemEntity itemEntity : items) {
            if (itemEntity.getCategories().containsAll(CategoriesToSearchIfExistInItem)) {
                returnValue.add(itemEntity);
            }
        }

        return returnValue;
    }

    @Override
    public List<Long> filterAuctions(List<String> categoryNameList, String description, String locationText, BigDecimal lowestPrice, BigDecimal highestPrice) {
        List<Long> returnValue = new ArrayList<>();

//        List<ItemEntity> items = itemRepo.findAll();
//        List<ItemEntity> items = itemRepo.findByEventStartedTrueAndEventFinishedFalse();
        List<ItemEntity> items = itemRepo.findByEventStartedTrue();

        List<ItemEntity> itemsAfterCategoriesFilter = doCategoriesFilter(categoryNameList, items); //filter No1

        List<ItemEntity> itemsAfterDescriptionFilter = doDescriptionFilter(description, itemsAfterCategoriesFilter); //filter No2

        List<ItemEntity> itemsAfterLocationFilter = doLocationFilter(locationText, itemsAfterDescriptionFilter); //filter No3

        List<ItemEntity> itemsAfterPriceFilter = doPriceFilter(lowestPrice, highestPrice, itemsAfterLocationFilter); //filter No4

        for (ItemEntity itemEntity : itemsAfterPriceFilter) {
            returnValue.add(itemEntity.getItemID());
        }

        return returnValue;

    }



}
