package com.ted.eBayDIT.entity;


import javax.persistence.*;

//@Entity
//@Table(name="bid")
//public class BidEntity {
//
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name="id")
//    private  int id;
//
//
//    //todo user bidder
//    //todo
//
//}

@Entity
@Table(name="Bid")
public class BidEntity {

    private BidderEntity Bidder;

    private String Time;

    private String Amount;

    public BidderEntity getBidder() {
        return Bidder;
    }

    public void setBidder(BidderEntity bidder) {
        Bidder = bidder;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}