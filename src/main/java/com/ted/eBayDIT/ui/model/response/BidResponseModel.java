package com.ted.eBayDIT.ui.model.response;

import java.math.BigDecimal;

public class BidResponseModel {
    private int id;
    private String time;
    private BigDecimal amount;

    private BidderResponseModel bidder;



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

    public BidderResponseModel getBidder() {
        return bidder;
    }

    public void setBidder(BidderResponseModel bidder) {
        this.bidder = bidder;
    }
}
