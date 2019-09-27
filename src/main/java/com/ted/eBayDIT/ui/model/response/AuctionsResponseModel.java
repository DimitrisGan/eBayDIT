package com.ted.eBayDIT.ui.model.response;


import com.ted.eBayDIT.dto.BidDto;
import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.ItemLocationDto;
import com.ted.eBayDIT.dto.PhotoDto;

import java.math.BigDecimal;
import java.util.List;

public class AuctionsResponseModel {

    private Long itemID;
    private String name;
    private BigDecimal buyPrice;
    private BigDecimal firstBid;
    private String country;
    private String started;
    private String ends;
    private String description;
    private List<CategoriesResponse> categories;

    private SellerResponseModel seller;
    private ItemLocationResponseModel location;

    private boolean eventStarted;
    private boolean eventFinished;

    private int winnerID;

    private List <BidResponseModel> bids;
    private BigDecimal currently;

    private List<PhotoResponseModel> photos;

    private PhotoResponseModel defaultPhoto;

    public AuctionsResponseModel() {}

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

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(BigDecimal firstBid) {
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

    public boolean isEventStarted() {
        return eventStarted;
    }

    public void setEventStarted(boolean eventStarted) {
        this.eventStarted = eventStarted;
    }

    public boolean isEventFinished() {
        return eventFinished;
    }

    public void setEventFinished(boolean eventFinished) {
        this.eventFinished = eventFinished;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }

    public List<PhotoResponseModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponseModel> photos) {
        this.photos = photos;
    }

    public List<BidResponseModel> getBids() {
        return bids;
    }

    public void setBids(List<BidResponseModel> bids) {
        this.bids = bids;
    }

    public BigDecimal getCurrently() {
        return currently;
    }

    public void setCurrently(BigDecimal currently) {
        this.currently = currently;
    }

    public PhotoResponseModel getDefaultPhoto() {
        return defaultPhoto;
    }

    public void setDefaultPhoto(PhotoResponseModel defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }


}
