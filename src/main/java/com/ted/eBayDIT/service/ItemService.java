package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.BidderDto;
import com.ted.eBayDIT.dto.BidDto;
import com.ted.eBayDIT.dto.ItemDto;

import java.util.List;

public interface ItemService {

    void addNewItem(ItemDto item);

    boolean itemExists(Long id);


    int updateItemInfo(/*todo ItemInfoDto*/);


    void startAuction(Long id);

    boolean userOwnsTheAuction(Long id);

    List<ItemDto> getAllUserAuctions();

    int deleteAuction(Long id);

    void editAuction(Long id);

    void addBid(Long id, BidderDto bidder, BidDto bid);
}
