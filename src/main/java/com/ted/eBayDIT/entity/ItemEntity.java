package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  int id;


    @Column(name="name" ,nullable=false ,unique = true)
    private String name;


    @Column(name="currently" ,nullable=false ,unique = true)
    private String currently;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "Category")
    private List<CategoryEntity> categories = new ArrayList<>();


    @Column(name="buy_price" ,nullable=false ,unique = true)
    private int buyPrice;



    @Column(name="description" ,nullable=false ,unique = true)
    private String description;





}


//<!ATTLIST Item             ItemID CDATA #REQUIRED>
//<!ELEMENT Name	   (#PCDATA)>
//<!ELEMENT Category	   (#PCDATA)>
//<!ELEMENT Currently	   (#PCDATA)>
//<!ELEMENT Buy_Price      (#PCDATA)>
//<!ELEMENT First_Bid	   (#PCDATA)>
//<!ELEMENT Number_of_Bids (#PCDATA)>
//<!ELEMENT Bids           (Bid*)>
//<!ELEMENT Bid          (Bidder, Time, Amount)>
//<!ATTLIST Bidder     UserID CDATA #REQUIRED
//        Rating CDATA #REQUIRED>
//<!ELEMENT Bidder     (Location?, Country?)>
//<!ELEMENT Time	   (#PCDATA)>
//<!ELEMENT Amount	   (#PCDATA)>
//<!ELEMENT Location	   (#PCDATA)>
//<!ATTLIST Location     Latitude  CDATA #IMPLIED
//        Longitude CDATA #IMPLIED>
//<!ELEMENT Country	   (#PCDATA)>
//<!ELEMENT Started	   (#PCDATA)>
//<!ELEMENT Ends	   (#PCDATA)>
//<!ELEMENT Seller	   EMPTY>
//<!ATTLIST Seller         UserID CDATA #REQUIRED
//        Rating CDATA #REQUIRED>
//<!ELEMENT Description	   (#PCDATA)>