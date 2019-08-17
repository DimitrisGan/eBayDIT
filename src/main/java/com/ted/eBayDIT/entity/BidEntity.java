package com.ted.eBayDIT.entity;



import lombok.Data;

import javax.persistence.*;

@Data

@Entity
@Table(name="bid")
public class BidEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private  int id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private ItemEntity itemDetails;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "bidder_id")
    private BidderDetailsEntity bidder;

    @Column
    private String time; //todo change type to Date //todo2 check not be the same from same bidder
    @Column
    private String amount;



}






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