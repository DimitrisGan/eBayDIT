package com.ted.eBayDIT.ui.model.request;


import com.ted.eBayDIT.dto.BidDto;

public class AddBidAuctionRequestModel {


    //    private String currently;
    //    private Integer numberOfBids;

//    private String ends;
//    BidDto bid ;

//    private String time; de to thelw mias kai tha vazw to current time egw

    private String amount;
    private Integer bidderId; //bidder-user id

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getBidderId() {
        return bidderId;
    }

    public void setBidderId(Integer bidderId) {
        this.bidderId = bidderId;
    }


//    private Long itemID; //OXI THA UPARXEI STO PATH TOU URI

//    private BidderDto bidder;

//    private Integer rating;
//    private String  country; //logika auta tha ta pairnw apo to user-bidderEntities
//    private String  location;










}
