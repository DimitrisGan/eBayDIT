package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.*;
import com.ted.eBayDIT.repository.*;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.PhotoService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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


    @Autowired
    PhotoService photoService;


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


    @Override
    public boolean auctionStarted(Long id) {
        return this.itemRepo.findByItemID(id).isEventStarted();
    }

    private boolean bidsInAuctionExist(Long id) {
        int num = this.itemRepo.findByItemID(id).getNumberOfBids();
        return num != 0;
    }



    @Override
    public boolean isAuctionFinishedByTime(Long id) throws ParseException {
        //check if is marked finished
        if (this.itemRepo.findByItemID(id).isEventFinished())
            return true; //if so then no need to do dates comparison

        String endsDateString = this.itemRepo.findByItemID(id).getEnds(); //TODO SOOOOOOOOOOOS NEEDS DEBUG HERE
        String currentDateString = Utils.getCurrentDateToStringDataType();
        Date currDate = Utils.convertStringDateToDateDataType(currentDateString);

//        Date endsDate = Utils.convertStringDateToDateDataType(endsDateString);  //this was before the Date format from front
        Date endsDate = Utils.convertStringDateToDateDataType(endsDateString);
        boolean isFinishedByTime = currDate.after(endsDate);
        if (isFinishedByTime) {
//            finishAuction(id); //here we make eventFinish value true
            ItemEntity item2save = itemRepo.findByItemID(id);
            List<BidEntity> bids= item2save.getBids();

            if (bids.isEmpty())
                item2save.setWinnerID(-1); //None won the item in the auction
            else
                item2save.setWinnerID(bids.get(bids.size()-1).getId()); //set winnerId

            item2save.setEventFinished(true);

            itemRepo.save(item2save);
        }
        return isFinishedByTime;
    }


//    private void finishAuction(Long id) {
//        if( ! this.itemRepo.findByItemID(id).isEventFinished() )
//            this.itemRepo.findByItemID(id).setEventFinished(true);
//    }




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

    private ItemEntity saveAuction(ItemEntity item) throws ParseException {
        /*set default values for item*/
        long itemID = generateNewItemID();  item.setItemID(itemID);

        item.setNumberOfBids(0);
        item.setStarted("Not started yet!");
        //todo item.setEnds(item.getEnds())

//        if (item.getFirstBid().equals(new BigDecimal("0")))
//            item.setCurrently(new BigDecimal("0")); //set Currenlty to 0
//        else
            item.setCurrently(item.getFirstBid());

        item.setEventStarted(false);
        item.setEventFinished(false);

//        item.setEnds(Utils.convertFrontDateTypeToBack(item.getEnds())); //this was before the Date format from front
        item.setEnds(item.getEnds());


        connectCategoriesToNewItem(item); //join item_categories table
        ItemLocationEntity location = this.itemLocationRepo.save(item.getLocation());   item.setLocation(location); //add item location
        //----------------------
        UserEntity currUser =  userRepo.findByUserId(this.securityService.getCurrentUser().getUserId());//modelMapper.map(this.securityService.getCurrentUser() , UserEntity.class);
        SellerDetailsEntity currSellerUser = this.sellerRepo.findById(currUser.getId());
        //----------------------
        item.setSeller(currSellerUser);

        return itemRepo.save(item);
    }


    private long generateNewItemID(){
        do { //generate a unique primary key for item
            newItemID++; //utils.generateItemID();
        }while(itemExists(newItemID));

        return newItemID;
    }

    @Override
    public ItemDto addNewItem(ItemDto item) throws ParseException {

        ModelMapper modelMapper = new ModelMapper();
        ItemEntity itemEntity2save = modelMapper.map(item, ItemEntity.class);

        //check if itemID already exists in db and throw exceptions
        if (itemExists(item)) throw new RuntimeException("item Record already exists");

        if (! categoriesExist(itemEntity2save.getCategories())) throw new RuntimeException("Categories Record dont exist");

        ItemEntity storedItemEntity = saveAuction(itemEntity2save);

        return modelMapper.map(storedItemEntity, ItemDto.class);
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
    public int deleteAuction(Long id) throws ParseException {

        if (! itemExists(id)) throw new RuntimeException("Auction id doesn't exists!");

        if ( isAuctionFinishedByTime(id) ) throw new RuntimeException("Auction has finished! Can't delete it now!");

        if ( bidsInAuctionExist(id)) throw new RuntimeException("Bids have been made thus cannot delete auction-Item now!");

        ItemEntity item = this.itemRepo.findByItemID(id);

        itemRepo.delete(item);

        return 0;
    }



    //todo
    @Override
    public void editAuction(Long id, ItemDto itemDto2update) throws ParseException {

        if (! itemExists(id)) throw new RuntimeException("Auction id doesn't exists!");

        if ( isAuctionFinishedByTime(id) ) throw new RuntimeException("Auction has finished! Can't edit it now!");

        if ( bidsInAuctionExist(id)) throw new RuntimeException("Bids have been made thus cannot edit auction-Item now!");


        //        UserRest returnValue =new UserRest();


        ItemEntity itemEntity = itemRepo.findByItemID(id);

//        if (itemDto2update.getEnds()    != null)    {itemEntity.setEnds(itemDto2update.getEnds()); }
//        if (user2update.getFirstName()  != null)    {userEntity.setFirstName(user2update.getFirstName()); }
//        if (user2update.getLastName()   != null)    {userEntity.setLastName(user2update.getLastName()); }
//
//        if (user2update.getLocation()        != null)    {userEntity.setLocation(user2update.getLocation()); }
//        if (user2update.getPhoneNumber()    != null)    {userEntity.setPhoneNumber(user2update.getPhoneNumber()); }
//        if (user2update.getCountry()        != null)    {userEntity.setCountry(user2update.getCountry()); }
//        if (user2update.getAfm()            != null)    {userEntity.setAfm(user2update.getAfm()); }


        itemRepo.save(itemEntity);


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
    public void addBid(Long auctionId, BigDecimal bidAmount, int bidderId) throws ParseException { //add bid to started auction

        ItemEntity item2save = this.itemRepo.findByItemID(auctionId); //get auction details

        //todo #3 edw prepei logika an exei teleiwsei na kanw to isFinished == true + na tsekarw me vash ta dates ends kai bid
        if (isAuctionFinishedByTime(item2save.getItemID())) throw new RuntimeException("Cannot bid in a finished auction!");


        BidderDetailsEntity bidder = this.bidderRepo.findById(bidderId);
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
    public void buyout(Long auctionId) throws ParseException {

        if (isAuctionFinishedByTime(auctionId)) throw new RuntimeException("Cannot buyout in a finished auction!"); //check if is ended

        if (bidsInAuctionExist(auctionId)) throw new RuntimeException("Buyout is not anymore in the table!You have to offer more ;) ");



        int bidderId = securityService.getCurrentUser().getId();

        ItemEntity item2save = this.itemRepo.findByItemID(auctionId); //get auction details

        if (item2save.getBuyPrice().equals(new BigDecimal(0))) throw new RuntimeException("There is no Buyout price set");

        item2save.setWinnerID(bidderId);//set winnerId
        item2save.setEnds(Utils.getCurrentDateToStringDataType());
        item2save.setEventFinished(true);

        itemRepo.save(item2save);

    }

    @Override
    public void saveImage(MultipartFile imageFile, PhotoDto photoDto) throws Exception {

        photoService.savePhotoImage(photoDto, imageFile);
        photoService.save(photoDto);
    }


    @Override
    public List<ItemDto> getAllUserAuctions() throws ParseException {
        List<ItemDto> returnList = new ArrayList<>();
        String userId = this.securityService.getCurrentUser().getUserId();
        UserEntity currUser = this.userRepo.findByUserId(userId);

        SellerDetailsEntity seller = sellerRepo.findById(currUser.getId());
        List<ItemEntity> returnEntitiesList = seller.getItems();
        ModelMapper modelMapper = new ModelMapper();

        /*cinvert items/auctions List from Entity to Dto datatype*/
        for (ItemEntity itemEntity : returnEntitiesList) {

            boolean uselessVar = isAuctionFinishedByTime(itemEntity.getItemID());

            ItemDto itemDto =  modelMapper.map(itemEntity, ItemDto.class);
            returnList.add(itemDto);
        }

        return returnList;
    }




//    @Override
//    public List<ItemDto> getAllUsersActiveAuctions()
//    @Override
//    public List<ItemDto> getAllUsersFinishedAuctions()
//    @Override
//    public List<ItemDto> getAllUsersNotStartedAuctions()





}
