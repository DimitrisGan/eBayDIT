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


    private void saveAuction(ItemEntity item) {
//        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
//        user.setRole(roleRepo.findByUserRole(RoleName.ADMIN.name()));
//        //todo put exception if user is null
        item.setItemID(newItemID);
        item.setNumberOfBids(0);
        item.setCurrently("---");

        ModelMapper modelMapper = new ModelMapper();
        UserEntity currUser =  modelMapper.map(this.securityService.getCurrentUser() , UserEntity.class);

        SellerDetailsEntity currSellerUser = new SellerDetailsEntity();

        currSellerUser.setUser(currUser);
        currSellerUser.setRating(0);
//        this.sellerRepo.save(currSellerUser);
//        ItemLocationEntity location = this.itemLocationRepo.save(item.getLocation());

//        item.setLocation(location);
//        currSellerUser = modelMapper.map(currUser , SellerDetailsEntity.class);

//        item.setSeller(currSellerUser);


        this.itemRepo.save(item);
    }



    @Override
    public int addNewItem(ItemDto item) {

        ModelMapper modelMapper = new ModelMapper();
        ItemEntity itemEntity2save = modelMapper.map(item, ItemEntity.class);


        do { //generate a unique primary key for item
            newItemID++; //utils.generateItemID();
        }while(itemIdExists(newItemID));

        //check if itemID already exists in db and throw exceptions
        if (itemExists(item)) throw new RuntimeException("item Record already exists");

        if (! categoriesExist(itemEntity2save.getCategories())) throw new RuntimeException("Categories Record dont exist");

        //todo edw prepei na prosthesw ola ta epipleon pedia pou thelw gia to new auction


//        itemEntity2save.set

//        saveAuction(itemEntity2save);

        itemEntity2save.setItemID(newItemID);
        itemEntity2save.setNumberOfBids(0);
        itemEntity2save.setCurrently("---");
//        categRepo.

        List<CategoryEntity> categoriesToAddList = itemEntity2save.getCategories();
//        itemEntity2save.getCategories().clear();

        int i=0;
        for (CategoryEntity cat : categoriesToAddList ) {
            CategoryEntity categ = categRepo.findByName(cat.getName());
//            categ.getItemDetails().add(itemEntity2save)
//;

            itemEntity2save.getCategories().set(i, categ);
//            itemEntity2save.getCategories().add(categ);
//            categRepo.save(categ);
            i++;
        }

        ItemEntity storedUserDetails =itemRepo.save(itemEntity2save);

        //todo isws convert se Dto kai epistrofh sto postman


        //todo add seller if not exist to table
        //private SellerDto seller;



//        private String name;
//        private String buyPrice;
//        private String firstBid;
//        private String country;
//        private String started;
//        private String ends;
//        private String description;
//
//        private List<CategoryDto> categories;
//        private SellerDto seller;
//        private ItemLocationDto location;


        //we have to store/save this info to itemEntity




//
//        String publicUserId =utils.generateUserId(30);
//        userEntity2save.setUserId(publicUserId);
//        userEntity2save.setRole(this.roleRepo.findByUserRole(RoleName.USER.name()));
//
//        //set verification =false to the newly created user
//        //todo maybe(?) add routine if its admin to me verified instantly
//        userEntity2save.setVerified(false);
//
//        storedUserDetails =  userRepo.save(userEntity2save);

        return 0;
    }






    @Override
    public int updateItemInfo() {
        return 0;
    }
}
