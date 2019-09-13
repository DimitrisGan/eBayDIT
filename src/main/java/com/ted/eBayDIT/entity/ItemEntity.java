package com.ted.eBayDIT.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name="item")
public class ItemEntity implements Serializable {

    private static final long serialVersionUID = -8863719389166568917L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @ManyToMany(fetch = FetchType.LAZY/*cascade={*//*CascadeType.PERSIST,*//*CascadeType.REFRESH, CascadeType.MERGE,
            CascadeType.DETACH ,CascadeType.REMOVE}*/)
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"), //refers to "item_id" column in "item_category" join  table
            inverseJoinColumns = @JoinColumn(name = "category_id") //refers to "category_id" column in "item_category" join  table
    )
    private List<CategoryEntity> categories;



    @OneToMany(mappedBy="itemDetails",fetch = FetchType.EAGER, cascade={/*CascadeType.PERSIST,*/CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.MERGE,
            CascadeType.DETACH})
    private List<BidEntity> bids ; //todo check if needs new table Bids

    @ManyToOne(cascade={/*CascadeType.PERSIST,*/CascadeType.REFRESH,/*CascadeType.REMOVE,*/ CascadeType.MERGE,
            CascadeType.DETACH})
    @JoinColumn(name="seller_id")
    private SellerDetailsEntity seller;

    @OneToOne(cascade={/*CascadeType.PERSIST,*/CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.MERGE,
            CascadeType.DETACH})
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private ItemLocationEntity location;

    @Column
    private boolean eventStarted;
    @Column
    private boolean eventFinished;

    @Column
    private String winnerUserId;


    @OneToMany(mappedBy="item", cascade={/*CascadeType.PERSIST,*/CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<PhotoEntity> photos;



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "item" /*, orphanRemoval = true*/)
    private List<VisitEntity> itemVisits;




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


    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoEntity> photos) {
        this.photos = photos;
    }

    public String getWinnerUserId() {
        return winnerUserId;
    }

    public void setWinnerUserId(String winnerUserId) {
        this.winnerUserId = winnerUserId;
    }

    public List<VisitEntity> getItemVisits() {
        return itemVisits;
    }

    public void setItemVisits(List<VisitEntity> itemVisits) {
        this.itemVisits = itemVisits;
    }
}