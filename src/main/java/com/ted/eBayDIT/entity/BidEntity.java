package com.ted.eBayDIT.entity;




import javax.persistence.*;
import java.math.BigDecimal;

//@Data

@Entity
@Table(name="bid")
public class BidEntity {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    @ManyToOne(cascade={/*CascadeType.PERSIST,*/CascadeType.REFRESH, CascadeType.MERGE,
            CascadeType.DETACH})
    @JoinColumn(name="item_id")
    private ItemEntity itemDetails;

    @ManyToOne(cascade=CascadeType.ALL)/*(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})*/
    @JoinColumn(name = "bidder_id")
    private BidderDetailsEntity bidder;

    @Column
    private String time; //todo change type to Date //todo2 check not be the same from same bidder
    @Column
    private BigDecimal amount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ItemEntity itemDetails) {
        this.itemDetails = itemDetails;
    }

    public BidderDetailsEntity getBidder() {
        return bidder;
    }

    public void setBidder(BidderDetailsEntity bidder) {
        this.bidder = bidder;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
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