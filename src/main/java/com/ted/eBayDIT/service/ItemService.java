package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ItemService {

    ItemDto addNewItem(ItemDto item) throws ParseException;

    boolean itemExists(Long id);

    void startAuction(Long id);

    boolean userOwnsTheAuction(Long id);

    List<ItemDto> getAllUserAuctions() throws ParseException;

    int deleteAuction(Long id) throws ParseException;

    void editAuction(Long id, ItemDto itemDto2store) throws ParseException;

    void addBid(Long auctionId, BigDecimal bidAmount, int bidderId) throws ParseException; //auctionId,bidAmount,bidderId)

    boolean auctionStarted(Long id);

    boolean isAuctionFinishedByTime(Long id) throws ParseException;

    void buyout(Long auctionId) throws ParseException;

    void saveImage(MultipartFile imageFile, PhotoDto photoDto) throws Exception;


}
