package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.text.ParseException;
import java.util.List;

public interface SearchService {


    List<ItemDto> getActiveAuctions() throws ParseException;

    //TODO AYRIO!!
    List<ItemDto> getFilteredAuctions(int pageNo, int pageSize, String sortBy, String orderType);
}
