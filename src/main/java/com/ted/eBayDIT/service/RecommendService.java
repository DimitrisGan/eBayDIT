package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.util.List;

public interface RecommendService {


    List<Long> getRecommendedAuctionIds();


    void createLsh();
}
