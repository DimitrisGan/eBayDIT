package com.ted.eBayDIT.dto;

import java.io.Serializable;

public class BidDto implements Serializable {
    private static final long serialVersionUID = 5676746430987885339L;

    private Integer id;
    private String time;
    private String amount;

    private BIdderDto bidder;
    private ItemDto itemDetails; //TODO maybe not needed


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BIdderDto getBidder() {
        return bidder;
    }

    public void setBidder(BIdderDto bidder) {
        this.bidder = bidder;
    }

    public ItemDto getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDto itemDetails) {
        this.itemDetails = itemDetails;
    }
}
