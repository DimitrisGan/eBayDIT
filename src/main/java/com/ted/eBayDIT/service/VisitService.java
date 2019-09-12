package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.UserDto;

public interface VisitService {


    void addVisit(UserDto userDto,Long auctionId);
}
