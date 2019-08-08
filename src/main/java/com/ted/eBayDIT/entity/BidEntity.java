package com.ted.eBayDIT.entity;


import javax.persistence.*;

@Entity
@Table(name="bid")
public class BidEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;


    //todo user bidder
    //todo

}
