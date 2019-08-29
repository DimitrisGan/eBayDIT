package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.entity.*;
import com.ted.eBayDIT.repository.*;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Utils utils;

    @Autowired
    private CategoryRepository categRepo;

    @Autowired
    SecurityService securityService;

    @Autowired
    SellerDetailsRepository sellerRepo;

    @Autowired
    BidderDetailsRepository bidderRepo;


    @Autowired
    ItemLocationRepo itemLocationRepo;

    private static long newItemID=0;



    private boolean itemExists(ItemDto item){
        ItemEntity itemCheck = this.itemRepo.findByItemID(item.getItemID());
        return itemCheck != null;
    }



    @Override //todo remove duplicate
    public boolean itemExists(Long id) {
        ItemEntity itemEntity = this.itemRepo.findByItemID(id);
        return itemEntity != null; //if userEntity is null return false
    }



//    public boolean isFinished(ItemEntity item){
//        return item.isEventFinished();
//    }
//
//    public boolean isStarted(ItemEntity item){
//        return item.isEventStarted();
//    }

    @Override
    public boolean auctionStarted(Long id) {
        return this.itemRepo.findByItemID(id).isEventStarted();
    }

    @Override
    public boolean auctionFinished(Long id) {
        return this.itemRepo.findByItemID(id).isEventFinished();
    }


    private boolean categoriesExist(List<CategoryEntity> categories){

        for (CategoryEntity category : categories) {
            CategoryEntity categ = categRepo.findByName(category.getName());
            if (categ == null)
                return false;
        }
        return true;
    }


    private void connectCategoriesToNewItem(ItemEntity item){
        /*here set-insert items_categories table*/
        List<CategoryEntity> categoriesToAddList = item.getCategories();
        int i=0;
        for (CategoryEntity cat : categoriesToAddList ) {
            CategoryEntity categ = categRepo.findByName(cat.getName());

            item.getCategories().set(i, categ);
            i++;
        }
    }

    private void saveAuction(ItemEntity item) {
        /*set default values for item*/
        long itemID = generateNewItemID();  item.setItemID(itemID);

        item.setNumberOfBids(0);
        item.setStarted(Utils.getCurrentDateToStringDataType());
        //todo item.setEnds(item.getEnds())

        if (item.getFirstBid().equals(new BigDecimal("0")))
            item.setCurrently(new BigDecimal("0")); //set Currenlty to 0
        else
            item.setCurrently(item.getFirstBid());

        item.setEventStarted(false);
        item.setEventFinished(false);
        connectCategoriesToNewItem(item); //join item_categories table
        ItemLocationEntity location = this.itemLocationRepo.save(item.getLocation());   item.setLocation(location); //add item location
        //----------------------
        UserEntity currUser =  userRepo.findByUserId(this.securityService.getCurrentUser().getUserId());//modelMapper.map(this.securityService.getCurrentUser() , UserEntity.class);
        SellerDetailsEntity currSellerUser = this.sellerRepo.findById(currUser.getId());
        //----------------------
        item.setSeller(currSellerUser);
        ItemEntity storedItemDetails =itemRepo.save(item);

    }


    private long generateNewItemID(){
        do { //generate a unique primary key for item
            newItemID++; //utils.generateItemID();
        }while(itemExists(newItemID));

        return newItemID;
    }

    @Override
    public void addNewItem(ItemDto item) {

        ModelMapper modelMapper = new ModelMapper();
        ItemEntity itemEntity2save = modelMapper.map(item, ItemEntity.class);

        //check if itemID already exists in db and throw exceptions
        if (itemExists(item)) throw new RuntimeException("item Record already exists");

        if (! categoriesExist(itemEntity2save.getCategories())) throw new RuntimeException("Categories Record dont exist");

        saveAuction(itemEntity2save);

        //todo maybe convert to Dto objeect and send it back to response

    }



    @Override
    public int updateItemInfo() {
        return 0;
    }

    @Override
    public void startAuction(Long id) {
        ItemEntity item = this.itemRepo.findByItemID(id);

        if (! itemExists(id)) throw new RuntimeException("Auction-Item id doesn't exists");


        item.setEventStarted(true);
        item.setStarted(Utils.getCurrentDateToStringDataType()); //set timeStarted with currentTime
        this.itemRepo.save(item);
    }

    @Override
    public boolean userOwnsTheAuction(Long id) {
        ItemEntity item = this.itemRepo.findByItemID(id);
        int userId = this.securityService.getCurrentUser().getId();
        List<ItemEntity> usersItems = this.sellerRepo.findById(userId).getItems();
        for (ItemEntity usersItem : usersItems) {
            if (usersItem.getItemID().equals(id))
                return true;
        }
        return false;
    }

    @Override
    public int deleteAuction(Long id) {

        if (! itemExists(id)) throw new RuntimeException("Auction-Item id doesn't exists!");

        if ( auctionIsStarted(id)) throw new RuntimeException("Auction-Item has already started! Can't delete it now!");

        ItemEntity item = this.itemRepo.findByItemID(id);

        itemRepo.delete(item);

        return 0;
    }

    private boolean auctionIsStarted(Long id) {
        int num = this.itemRepo.findByItemID(id).getNumberOfBids();
        return num != 0;
    }


    @Override
    public void editAuction(Long id) {
        //todo edit fields of auction!!!
        //todo #2 prepei na to kanw

    }



    private BidEntity createBid (BigDecimal amount , ItemEntity itemDetails, BidderDetailsEntity bidder){
        BidEntity returnValue = new BidEntity();

        returnValue.setAmount(amount);
        returnValue.setTime(Utils.getCurrentDateToStringDataType());
        returnValue.setItemDetails(itemDetails);
        returnValue.setBidder(bidder);

        return  returnValue;
    }
    @Override
    public void addBid(Long auctionId, BigDecimal bidAmount, int bidderId){ //add bid to started auction

        ItemEntity item2save = this.itemRepo.findByItemID(auctionId); //get auction details
        BidderDetailsEntity bidder = this.bidderRepo.findById(bidderId);

        //todo #3 edw prepei logika an exei teleiwsei na kanw to isFinished == true + na tsekarw me vash ta dates ends kai bid
        if (auctionFinished(item2save.getItemID())) throw new RuntimeException("Cannot bid in a finished auction!");


        if (bidder.getId() == this.securityService.getCurrentUser().getId()) throw new RuntimeException("Seller cannot bid in his own auction!");


        int res = item2save.getCurrently().compareTo(bidAmount);
        if (res >=0 ) throw new RuntimeException("Bid cannot be less than current best offer or first bid!"); //Source: https://www.tutorialspoint.com/java/math/bigdecimal_compareto.htm


        item2save.setCurrently(bidAmount);
        BidEntity bidEntity2save = createBid(bidAmount,item2save,bidder);

        item2save.getBids().add(bidEntity2save);
        item2save.setNumberOfBids(item2save.getNumberOfBids()+1); // numOfBids++;


        this.itemRepo.save(item2save);

    }



    @Override
    public List<ItemDto> getAllUserAuctions() {
        List<ItemDto> returnList = new ArrayList<>();
        String userId = this.securityService.getCurrentUser().getUserId();
        UserEntity currUser = this.userRepo.findByUserId(userId);

        SellerDetailsEntity seller = sellerRepo.findById(currUser.getId());
        List<ItemEntity> returnEntitiesList = seller.getItems();
        ModelMapper modelMapper = new ModelMapper();

        /*cinvert items/auctions List from Entity to Dto datatype*/
        for (ItemEntity itemEntity : returnEntitiesList) {

            //todo # here chech the current time with ends and set isEventFinished with true
            ItemDto itemDto =  modelMapper.map(itemEntity, ItemDto.class);
            returnList.add(itemDto);
        }

        return returnList;
    }







}
