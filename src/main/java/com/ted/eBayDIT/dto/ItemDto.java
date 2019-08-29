package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ItemDto implements Serializable {

    private static final long serialVersionUID = -8580425406485881385L;

    private Long itemID;
    private String name;
    private BigDecimal buyPrice;
    private BigDecimal firstBid;
    private String country;
    private String started;
    private String ends;
    private String description;
    private List<CategoryDto> categories;
    private SellerDto seller;
    private ItemLocationDto location;


    private Integer numberOfBids;
    private String currently;

    private List<BidDto> bids ;

    private boolean eventStarted;
    private boolean eventFinished;



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

    public String getCurrently() {
        return currently;
    }

    public void setCurrently(String currently) {
        this.currently = currently;
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

    public Integer getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Integer numberOfBids) {
        this.numberOfBids = numberOfBids;
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

    public ItemLocationDto getLocation() {
        return location;
    }

    public void setLocation(ItemLocationDto location) {
        this.location = location;
    }

    public List<BidDto> getBids() {
        return bids;
    }

    public void setBids(List<BidDto> bids) {
        this.bids = bids;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public SellerDto getSeller() {
        return seller;
    }

    public void setSeller(SellerDto seller) {
        this.seller = seller;
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
}
