package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.util.List;

public interface ItemService {

    void addNewItem(ItemDto item);

    boolean itemExists(Long id);


    int updateItemInfo(/*todo ItemInfoDto*/);


    void startAuction(Long id);

    boolean userOwnsTheAuction(Long id);

    List<ItemDto> getAllUserAuctions();
}
