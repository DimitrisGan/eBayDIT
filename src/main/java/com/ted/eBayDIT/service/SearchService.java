package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface SearchService {


    List<ItemDto> getActiveAuctions() throws ParseException;

    List<ItemDto> getFilteredAuctions(int pageNo, int pageSize, String sortBy, String sortType);


    List<ItemDto> filterAuctions(List<String> categoryNameList, String description, String locationText, BigDecimal lowestPrice, BigDecimal highestPrice);
}
