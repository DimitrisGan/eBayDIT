package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.math.BigDecimal;
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

    void addBid(Long auctionId, BigDecimal bidAmount, int bidderId); //auctionId,bidAmount,bidderId)

    boolean auctionStarted(Long id);
}
