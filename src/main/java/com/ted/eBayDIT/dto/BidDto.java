package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BidDto implements Serializable {
    private static final long serialVersionUID = 5676746430987885339L;

    private int id;
    private String time;
    private BigDecimal amount;

    private BidderDto bidder;
    private ItemDto itemDetails; //TODO maybe not needed


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BidderDto getBidder() {
        return bidder;
    }

    public void setBidder(BidderDto bidder) {
        this.bidder = bidder;
    }

    public ItemDto getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemDto itemDetails) {
        this.itemDetails = itemDetails;
    }
}
