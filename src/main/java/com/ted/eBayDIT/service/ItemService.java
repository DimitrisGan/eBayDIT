package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ItemService {

    void addNewItem(ItemDto item);

    boolean itemExists(Long id);

    void startAuction(Long id);

    boolean userOwnsTheAuction(Long id);

    List<ItemDto> getAllUserAuctions() throws ParseException;

    int deleteAuction(Long id) throws ParseException;

    void editAuction(Long id) throws ParseException;

    void addBid(Long auctionId, BigDecimal bidAmount, int bidderId) throws ParseException; //auctionId,bidAmount,bidderId)

    boolean auctionStarted(Long id);

    boolean isAuctionFinished(Long id) throws ParseException;

    void buyout(Long auctionId) throws ParseException;
}
