package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.entity.VisitEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.repository.VisitRepository;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        VisitEntity visit= this.visitRepo.findByVisitorAndAndItem(userEntity,itemEntity);

        if (visit == null) //create new visitEntity
            createNewVisitEntity(userEntity,itemEntity);
        else //counter++
            addVisit(visit);



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
