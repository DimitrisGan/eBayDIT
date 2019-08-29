package com.ted.eBayDIT.entity;



import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

//@Entity
//@Table(name="item")
//public class ItemEntity {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name="id")
//    private  int id;
//
//
//    @Column(name="name" ,nullable=false ,unique = true)
//    private String name;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
//            mappedBy = "CategoryEntity")
//    private List<CategoryEntity> categories = new ArrayList<>();
//
//
//
//
//    @Column(name="currently" ,nullable=false )
//    private String currently;
//
//
//    @Column(name="buy_price" )
//    private int buyPrice;
//
//    @Column(name="first_bid" )
//    private int firstBid;
//
//    @Column(name="number_of_bids" )
//    private int numberOfBids;
//
//    @Column(name="bids" )
//    private int bids;
//
////    @OneToMany(name="bids",cascade = CascadeType.ALL, fetch = FetchType.LAZY,
////            mappedBy = "BidEntity")
////    private List<BidEntity> bids = new ArrayList<>();
//
//    @Column(name="description" ,nullable=false ,unique = true)
//    private String description;
//
//
//}


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

//@Data

@Entity
@Table(name="item")
public class ItemEntity {


    @Id
    @Column(name="item_id"/*,nullable = false*/,unique = true)
    private Long itemID;

    @Column(name="name" ,nullable=false)
    private String name;

    @Column(name="currently" /*,nullable=false */)
    private BigDecimal currently;

    @Column(name="buy_price" /*,nullable=false */)
    private BigDecimal buyPrice;

    @Column(name="first_bid" /*,nullable=false */)
    private BigDecimal firstBid;

    @Column(name="number_of_bids" /*,nullable=false */)
    private Integer numberOfBids;

    @Column
    private String country;
    @Column
    private String started;
    @Column
    private String ends;

    @Lob //LONGTEXT for description column
    @Column
    private String description;


    //Do not apply cascading deletes!
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"), //refers to "item_id" column in "item_category" join  table
            inverseJoinColumns = @JoinColumn(name = "category_id") //refers to "category_id" column in "item_category" join  table
    )
    private List<CategoryEntity> categories;



    @OneToMany(mappedBy="itemDetails", cascade=CascadeType.ALL)
    private List<BidEntity> bids ; //todo check if needs new table Bids

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="seller_id")
    private SellerDetailsEntity seller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private ItemLocationEntity location;

    @Column
    private boolean eventStarted;
    @Column
    private boolean eventFinished;

    @Column
    private int winnerID;


    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrently() {
        return currently;
    }

    public void setCurrently(BigDecimal currently) {
        this.currently = currently;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(BigDecimal firstBid) {
        this.firstBid = firstBid;
    }

    public Integer getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Integer numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<BidEntity> getBids() {
        return bids;
    }

    public void setBids(List<BidEntity> bids) {
        this.bids = bids;
    }

    public SellerDetailsEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerDetailsEntity seller) {
        this.seller = seller;
    }

    public ItemLocationEntity getLocation() {
        return location;
    }

    public void setLocation(ItemLocationEntity location) {
        this.location = location;
    }

    public boolean isEventStarted() {
        return eventStarted;
    }

    public void setEventStarted(boolean eventStarted) {
        this.eventStarted = eventStarted;
    }

    public boolean isEventFinished() {
        return eventFinished;
    }

    public void setEventFinished(boolean eventFinished) {
        this.eventFinished = eventFinished;
    }

    public int getWinnerID() {
        return winnerID;
    }

    public void setWinnerID(int winnerID) {
        this.winnerID = winnerID;
    }
}