package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Utils utils;



    private boolean itemExists(ItemDto item){
        ItemEntity itemCheck = this.itemRepo.findByItemID(item.getItemID());
        return itemCheck != null;
    }

    @Override
    public int addNewItem(ItemDto item) {

        //check if item already exists in db and throw exceptions
        if (itemExists(item)) throw new RuntimeException("item Record already exists");

        //we have to store/save this info to itemEntity
        ItemEntity itemEntity2save = new ItemEntity();
        ModelMapper modelMapper = new ModelMapper();
        itemEntity2save = modelMapper.map(item, ItemEntity.class);


//        itemEntity2save.setItemID(utils.generateUserId());

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
