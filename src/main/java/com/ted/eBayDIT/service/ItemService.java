package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

public interface ItemService {

    int addNewItem(ItemDto item);

    int updateItemInfo(/*todo ItemInfoDto*/);


    void startAuction(Long id);

    boolean userOwnsTheAuction(Long id);
}
