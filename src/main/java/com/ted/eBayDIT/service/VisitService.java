package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;

import java.util.List;

public interface VisitService {


    void addVisit(UserDto userDto,Long auctionId);

    List<Long> getMostVisitedAuctions(int numOfAuction2recommend);
}
