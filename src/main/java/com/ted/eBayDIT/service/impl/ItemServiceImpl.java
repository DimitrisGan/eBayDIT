package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.CategoryDto;
import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.PhotoDto;
import com.ted.eBayDIT.entity.*;
import com.ted.eBayDIT.repository.*;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ConnectivityService;
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
    ItemLocationRepository itemLocationRepo;


    @Autowired
    PhotoService photoService;

    @Autowired
    ConnectivityService connectivityService;


    private static long newItemID=0;



    private boolean itemExists(ItemDto item){
        ItemEntity itemCheck = this.itemRepo.findByItemID(item.getItemID());
        return itemCheck != null;
    }

    @Override
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

        String endsDateString = this.itemRepo.findByItemID(id).getEnds();
        String currentDateString = Utils.getCurrentDateToStringDataType();
        Date currDate = Utils.convertStringDateToDateDataType(currentDateString);

//        Date endsDate = Utils.convertStringDateToDateDataType(endsDateString);  //this was before the Date format from front
        Date endsDate = Utils.convertStringDateToDateDataType(endsDateString);
        boolean isFinishedByTime = currDate.after(endsDate);
        if (isFinishedByTime) {
            ItemEntity item2save = itemRepo.findByItemID(id);
            List<BidEntity> bids= item2save.getBids();

            if (bids.isEmpty())
                item2save.setWinnerUserId("NOBODY WON!"); //None won the item in the auction
            else {
                UserEntity winnerEntity = bids.get(bids.size() - 1).getBidder().getUser();
                UserEntity sellerEntity = this.itemRepo.findByItemID(id).getSeller().getUser();

                item2save.setWinnerUserId(winnerEntity.getUserId()); //set winnerId

                if (! connectivityService.exist(winnerEntity,sellerEntity))
                    connectivityService.createConnection(winnerEntity,sellerEntity);

            }
            item2save.setEventFinished(true);

            itemRepo.save(item2save);
        }
        return isFinishedByTime;
    }



    @Override
    public void buyout(Long auctionId) throws ParseException {

        if (isAuctionFinishedByTime(auctionId)) throw new RuntimeException("Cannot buyout in a finished auction!"); //check if is ended

        if (bidsInAuctionExist(auctionId)) throw new RuntimeException("Buyout is not anymore in the table!You have to offer more ;) ");

        ItemEntity item2save = this.itemRepo.findByItemID(auctionId); //get auction details

        if (item2save.getBuyPrice().equals(new BigDecimal(0))) throw new RuntimeException("There is no Buyout price set");


        String winnerUserId = securityService.getCurrentUser().getUserId();
        UserEntity winnerEntity = this.userRepo.findByUserId(winnerUserId);

        UserEntity sellerEntity = item2save.getSeller().getUser();

        item2save.setWinnerUserId(winnerUserId);//set winnerId to the winner userId
        item2save.setCurrently(item2save.getBuyPrice());

        if (! connectivityService.exist(winnerEntity,sellerEntity))
            connectivityService.createConnection(winnerEntity,sellerEntity);

        item2save.setEnds(Utils.getCurrentDateToStringDataType());
        item2save.setEventFinished(true);

        itemRepo.save(item2save);

    }


//    private void finishAuction(Long id) {
//        if( ! this.itemRepo.findByItemID(id).isEventFinished() )
//            this.itemRepo.findByItemID(id).setEventFinished(true);
//    }

    private boolean categoriesExist(List<CategoryEntity> categories){

        int i=0;
        int prevCategId=-1; //root category
        CategoryEntity categ;
        for (CategoryEntity category : categories) {
            if (i==0){
                categ = categRepo.findByName(category.getName());

                if (categ == null)
                    return false;

            }
            else {

                categ = categRepo.findByNameAndParentId(category.getName() , prevCategId);
                if (categ == null)
                    return false;

            }
            i++;
            prevCategId = categ.getId();

        }
        return true;
    }


    private void connectCategoriesToNewItem(ItemEntity item){
        /*here set-insert items_categories table*/
        List<CategoryEntity> categoriesToAddList = item.getCategories();
        int i=0;
        int prevCategId=-1; //root category
        CategoryEntity categ;
        for (CategoryEntity cat : categoriesToAddList ) {

            categ = categRepo.findByNameAndParentId(cat.getName(),prevCategId);

            item.getCategories().set(i, categ);
            prevCategId = categ.getId();
            i++;
        }
    }

    private ItemEntity saveAuction(ItemEntity item) throws ParseException {
        /*set default values for item*/
//        long itemID = generateNewItemID();  item.setItemID(itemID);

        item.setNumberOfBids(0);
        item.setStarted("Not started yet!");
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

    @Override
    public void editAuction(Long id, ItemDto itemDto2update) throws ParseException {

        if (! itemExists(id)) throw new RuntimeException("Auction id doesn't exists!");

        if ( isAuctionFinishedByTime(id) ) throw new RuntimeException("Auction has finished! Can't edit it now!");

        if ( bidsInAuctionExist(id)) throw new RuntimeException("Bids have been made thus cannot edit auction-Item now!");

        ItemEntity itemEntity = itemRepo.findByItemID(id);

        if (itemDto2update.getName()        != null)    {itemEntity.setName(itemDto2update.getName()); }

        if (itemDto2update.getBuyPrice()    != null)    {itemEntity.setBuyPrice(itemDto2update.getBuyPrice()); }

        if (itemDto2update.getFirstBid()    != null)    {itemEntity.setFirstBid(itemDto2update.getFirstBid()); }

        if (itemDto2update.getCountry()     != null)    {itemEntity.setCountry(itemDto2update.getCountry()); }

        if (itemDto2update.getEnds()        != null)    {itemEntity.setEnds(itemDto2update.getEnds()); }

        if (itemDto2update.getDescription() != null)    {itemEntity.setDescription(itemDto2update.getDescription()); }

        if (itemDto2update.getLocation()    != null){
            ModelMapper modelMapper = new ModelMapper();
            ItemLocationEntity locEnt = modelMapper.map(itemDto2update.getLocation(), ItemLocationEntity.class);
            itemEntity.setLocation(locEnt);
        }

        if (itemDto2update.getCategories()  != null) {
            itemEntity.getCategories().clear();
            ModelMapper modelMapper = new ModelMapper();
            CategoryEntity categEntity = new CategoryEntity();
            for (CategoryDto categoryDto : itemDto2update.getCategories()) {
                categEntity = modelMapper.map(categoryDto, CategoryEntity.class);
                itemEntity.getCategories().add(categEntity);
            }
        }

        connectCategoriesToNewItem(itemEntity); //join item_categories table


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


        if (isAuctionFinishedByTime(item2save.getItemID())) throw new RuntimeException("Cannot bid in a finished auction!");


        BidderDetailsEntity bidder = this.bidderRepo.findById(bidderId);
        if (bidder.getId() == item2save.getSeller().getId()) throw new RuntimeException("Seller cannot bid in his own auction!");


        //des edw gia an einai arxiko bid tote oxi !!!!
        int res = item2save.getCurrently().compareTo(bidAmount);
        if (item2save.getBids().size()==0 && res >0 ) throw new RuntimeException("First Bid cannot be less than first bid price!"); //Source: https://www.tutorialspoint.com/java/math/bigdecimal_compareto.htm



        if (item2save.getBids().size()!=0 && res >=0 ) throw new RuntimeException("Bid cannot be less than current best offer!"); //Source: https://www.tutorialspoint.com/java/math/bigdecimal_compareto.htm


        item2save.setCurrently(bidAmount);
        BidEntity bidEntity2save = createBid(bidAmount,item2save,bidder);

        item2save.getBids().add(bidEntity2save);
        item2save.setNumberOfBids(item2save.getNumberOfBids()+1); // numOfBids++;


        this.itemRepo.save(item2save);

    }





    @Override
    public void saveImage(MultipartFile imageFile, PhotoDto photoDto) throws Exception {

        photoService.savePhotoImage(photoDto, imageFile);
        photoService.save(photoDto);
    }

    @Override
    public ItemDto getItem(Long id) {

        ItemEntity itemEntity = itemRepo.findByItemID(id);

        if (itemEntity == null) throw new RuntimeException("Item-Auction doesn't exist");


        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(itemEntity, ItemDto.class);
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
