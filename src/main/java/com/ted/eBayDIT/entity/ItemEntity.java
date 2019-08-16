//package com.ted.eBayDIT.entity;
//
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
////@Entity
////@Table(name="item")
////public class ItemEntity {
////
////    @Id
////    @GeneratedValue(strategy= GenerationType.IDENTITY)
////    @Column(name="id")
////    private  int id;
////
////
////    @Column(name="name" ,nullable=false ,unique = true)
////    private String name;
////
////    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
////            mappedBy = "CategoryEntity")
////    private List<CategoryEntity> categories = new ArrayList<>();
////
////
////
////
////    @Column(name="currently" ,nullable=false )
////    private String currently;
////
////
////    @Column(name="buy_price" )
////    private int buyPrice;
////
////    @Column(name="first_bid" )
////    private int firstBid;
////
////    @Column(name="number_of_bids" )
////    private int numberOfBids;
////
////    @Column(name="bids" )
////    private int bids;
////
//////    @OneToMany(name="bids",cascade = CascadeType.ALL, fetch = FetchType.LAZY,
//////            mappedBy = "BidEntity")
//////    private List<BidEntity> bids = new ArrayList<>();
////
////    @Column(name="description" ,nullable=false ,unique = true)
////    private String description;
////
////
////}
//
//
////<!ATTLIST Item             ItemID CDATA #REQUIRED>
////<!ELEMENT Name	   (#PCDATA)>
////<!ELEMENT Category	   (#PCDATA)>
////<!ELEMENT Currently	   (#PCDATA)>
////<!ELEMENT Buy_Price      (#PCDATA)>
////<!ELEMENT First_Bid	   (#PCDATA)>
////<!ELEMENT Number_of_Bids (#PCDATA)>
////<!ELEMENT Bids           (Bid*)>
////<!ELEMENT Bid          (Bidder, Time, Amount)>
////<!ATTLIST Bidder     UserID CDATA #REQUIRED
////        Rating CDATA #REQUIRED>
////<!ELEMENT Bidder     (Location?, Country?)>
////<!ELEMENT Time	   (#PCDATA)>
////<!ELEMENT Amount	   (#PCDATA)>
////<!ELEMENT Location	   (#PCDATA)>
////<!ATTLIST Location     Latitude  CDATA #IMPLIED
////        Longitude CDATA #IMPLIED>
////<!ELEMENT Country	   (#PCDATA)>
////<!ELEMENT Started	   (#PCDATA)>
////<!ELEMENT Ends	   (#PCDATA)>
////<!ELEMENT Seller	   EMPTY>
////<!ATTLIST Seller         UserID CDATA #REQUIRED
////        Rating CDATA #REQUIRED>
////<!ELEMENT Description	   (#PCDATA)>
//
//
//@Entity
//@Table(name="Item")
//public class ItemEntity {
//
//
//    @Id
//    private String ItemID;
//
//    private String Name;
////    private List<CategoryEntity> Category = new ArrayList<>();
////    private List<String> Category = new ArrayList<>();
//    private String Currently;
//
//    private String Buy_Price;
//
//    private String First_Bid;
//    private String Number_of_Bids;
//
//    @OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="instructor_detail_ide" +
//            "")
//    private BidsEntity Bids ; //todo check if needs new table Bids
//
////    private LocationEntity Location;
//    private String Country;
//    private String Started;
//
//    private String Ends;
//
//
////    private SellerEntity Seller;
//    private String Description;
//
//
//    public String getItemID() {
//        return ItemID;
//    }
//
//    public void setItemID(String itemID) {
//        ItemID = itemID;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
////    public List<String> getCategory() {
////        return Category;
////    }
////
////    public void setCategory(List<String> category) {
////        Category = category;
////    }
//
//    public String getCurrently() {
//        return Currently;
//    }
//
//    public void setCurrently(String currently) {
//        Currently = currently;
//    }
//
//    public String getBuy_Price() {
//        return Buy_Price;
//    }
//
//    public void setBuy_Price(String buy_Price) {
//        Buy_Price = buy_Price;
//    }
//
//    public String getFirst_Bid() {
//        return First_Bid;
//    }
//
//    public void setFirst_Bid(String first_Bid) {
//        First_Bid = first_Bid;
//    }
//
//    public String getNumber_of_Bids() {
//        return Number_of_Bids;
//    }
//
//    public void setNumber_of_Bids(String number_of_Bids) {
//        Number_of_Bids = number_of_Bids;
//    }
//
//    public BidsEntity getBids() {
//        return Bids;
//    }
//
//    public void setBids(BidsEntity bids) {
//        Bids = bids;
//    }
//
////    public LocationEntity getLocation() {
////        return Location;
////    }
////
////    public void setLocation(LocationEntity location) {
////        Location = location;
////    }
//
//    public String getCountry() {
//        return Country;
//    }
//
//    public void setCountry(String country) {
//        Country = country;
//    }
//
//    public String getStarted() {
//        return Started;
//    }
//
//    public void setStarted(String started) {
//        Started = started;
//    }
//
//    public String getEnds() {
//        return Ends;
//    }
//
//    public void setEnds(String ends) {
//        Ends = ends;
//    }
//
////    public SellerEntity getSeller() {
////        return Seller;
////    }
////
////    public void setSeller(SellerEntity seller) {
////        Seller = seller;
////    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//}