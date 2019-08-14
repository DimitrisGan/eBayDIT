package com.ted.eBayDIT.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Bids")
public class BidsEntity {

    private List<BidEntity> Bid = new ArrayList<>(); //todo check if needs new table Bids


    public List<BidEntity> getBid() {
        return Bid;
    }

    public void setBid(List<BidEntity> bid) {
        Bid = bid;
    }
}
