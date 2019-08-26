package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.*;
import com.ted.eBayDIT.repository.*;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    @Autowired
//    UserService userService;


    @Autowired
    SecurityService securityService;

    @Autowired
    SellerDetailsRepository sellerRepo;


    @Autowired
    ItemLocationRepo itemLocationRepo;

    private static long newItemID=0;


    
    private boolean itemExists(ItemDto item){
        ItemEntity itemCheck = this.itemRepo.findByItemID(item.getItemID());
        return itemCheck != null;
    }

    private boolean itemIdExists(Long itemID){
        ItemEntity itemCheck = this.itemRepo.findByItemID(itemID);
        return itemCheck != null;
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
        item.setCurrently("---");
        item.setEventStarted(false);
        connectCategoriesToNewItem(item); //join item_categories table
        ItemLocationEntity location = this.itemLocationRepo.save(item.getLocation());   item.setLocation(location); //add item location
        //----------------------
        UserEntity currUser =  userRepo.findByUserId(this.securityService.getCurrentUser().getUserId());//modelMapper.map(this.securityService.getCurrentUser() , UserEntity.class);
        SellerDetailsEntity currSellerUser = this.sellerRepo.findById(currUser.getId());
        //currSellerUser.getItems().add(item);
        //----------------------
        item.setSeller(currSellerUser);
        ItemEntity storedItemDetails =itemRepo.save(item);

    }


    private long generateNewItemID(){
        do { //generate a unique primary key for item
            newItemID++; //utils.generateItemID();
        }while(itemIdExists(newItemID));

        return newItemID;
    }

    @Override
    public int addNewItem(ItemDto item) {

        ModelMapper modelMapper = new ModelMapper();
        ItemEntity itemEntity2save = modelMapper.map(item, ItemEntity.class);

        //check if itemID already exists in db and throw exceptions
        if (itemExists(item)) throw new RuntimeException("item Record already exists");

        if (! categoriesExist(itemEntity2save.getCategories())) throw new RuntimeException("Categories Record dont exist");

        saveAuction(itemEntity2save);

        //todo maybe convert to Dto objeect and send it back to response

        return 0;
    }






    @Override
    public int updateItemInfo() {
        return 0;
    }
}
