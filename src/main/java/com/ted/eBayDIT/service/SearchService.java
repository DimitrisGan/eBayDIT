package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.ItemDto;

import java.text.ParseException;
import java.util.List;

public interface SearchService {


    List<ItemDto> getActiveAuctions() throws ParseException;
}
