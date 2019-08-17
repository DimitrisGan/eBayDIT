//package com.ted.eBayDIT.entity;
//
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name="Bids")
//public class BidsEntity {
//
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name="id")
//    private  int id;
//
//    private List<BidEntity> Bid = new ArrayList<>(); //todo check if needs new table Bids
//
//
//    public List<BidEntity> getBid() {
//        return Bid;
//    }
//
//    public void setBid(List<BidEntity> bid) {
//        Bid = bid;
//    }
//}
