package com.ted.eBayDIT.ui.model.response;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.ItemLocationDto;

import java.util.List;

public class AuctionsResponseModel {

    //todo needs to add bidder !!!!
    private Long itemID;
    private String name;
    private String buyPrice;
    private String firstBid;
    private String country;
    private String started;
    private String ends;
    private String description;
    private List<CategoriesResponse> categories;

    private SellerResponseModel seller;
    private ItemLocationResponseModel location;

    public AuctionsResponseModel() {
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

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

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
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

    public List<CategoriesResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesResponse> categories) {
        this.categories = categories;
    }

    public SellerResponseModel getSeller() {
        return seller;
    }

    public void setSeller(SellerResponseModel seller) {
        this.seller = seller;
    }

    public ItemLocationResponseModel getLocation() {
        return location;
    }

    public void setLocation(ItemLocationResponseModel location) {
        this.location = location;
    }


//    private List<ItemDto> auctions;
//
//    public List<ItemDto> getAuctions() {
//        return auctions;
//    }
//
//    public void setAuctions(List<ItemDto> auctions) {
//        this.auctions = auctions;
//    }
}
