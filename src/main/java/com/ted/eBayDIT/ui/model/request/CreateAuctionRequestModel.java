package com.ted.eBayDIT.ui.model.request;


import com.ted.eBayDIT.dto.CategoryDto;
import com.ted.eBayDIT.dto.ItemLocationDto;
import com.ted.eBayDIT.dto.SellerDto;

import java.util.List;

public class CreateAuctionRequestModel {

//    private Long itemID;
//    private String currently;

    private String name;
    private String buyPrice;
    private String firstBid;
    private String country;

    private String ends;
    private String description;

    private List<CategoryDto> categories;
//    private SellerDto seller;
    private ItemLocationDto location;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(String firstBid) {
        this.firstBid = firstBid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

//    public SellerDto getSeller() {
//        return seller;
//    }
//
//    public void setSeller(SellerDto seller) {
//        this.seller = seller;
//    }

    public ItemLocationDto getLocation() {
        return location;
    }

    public void setLocation(ItemLocationDto location) {
        this.location = location;
    }
}
