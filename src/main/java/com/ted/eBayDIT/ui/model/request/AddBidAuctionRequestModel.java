package com.ted.eBayDIT.ui.model.request;


import com.ted.eBayDIT.dto.BidDto;

import java.math.BigDecimal;

public class AddBidAuctionRequestModel {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
