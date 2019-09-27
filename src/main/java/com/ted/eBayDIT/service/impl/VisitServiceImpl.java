package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.entity.VisitEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.repository.VisitRepository;
import com.ted.eBayDIT.service.SearchService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VisitRepository visitRepo;


    @Override
    public void addVisit(UserDto userDto,Long auctionId) {

        UserEntity userEntity = this.userRepo.findByUserId(userDto.getUserId());

        ItemEntity itemEntity = this.itemRepo.findByItemID(auctionId);

        if (userEntity == null) throw new RuntimeException("User doens't exist in [VisitService]!");

        if (itemEntity == null) throw new RuntimeException("Auction doens't exist in [VisitService]!");


        VisitEntity visit= this.visitRepo.findByVisitorAndItem(userEntity,itemEntity);

        if (visit == null) //create new visitEntity
            createNewVisitEntity(userEntity,itemEntity);
        else //counter++
            addVisit(visit);

    }


    @Override
    public List<Long> getMostVisitedAuctions(int numOfAuction2recommend) {
        List<Long> returnValue= new ArrayList<>();

        List<VisitEntity> visitedAuctions = this.visitRepo.findAll();

        visitedAuctions.sort(Collections.reverseOrder());

        if (numOfAuction2recommend > visitedAuctions.size()) //make sure to not exceed the visit entities in db
            numOfAuction2recommend = visitedAuctions.size();

        for (int i = 0; i < numOfAuction2recommend; i++) {
            Long itemId2add = visitedAuctions.get(i).getItem().getItemID();
            returnValue.add(itemId2add);
        }

        return  returnValue;
    }


    private void createNewVisitEntity(UserEntity user,ItemEntity item){
        VisitEntity newVisitEntity = new VisitEntity();
        newVisitEntity.setVisitor(user);
        newVisitEntity.setItem(item);
        newVisitEntity.setVisitsTimes(1);

        visitRepo.save(newVisitEntity);
    }

    private void addVisit(VisitEntity visit){
        int times = visit.getVisitsTimes();
        visit.setVisitsTimes(times+1);

        visitRepo.save(visit);

    }



}
