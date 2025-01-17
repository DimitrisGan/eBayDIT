package com.ted.eBayDIT.entity;

import javax.persistence.*;


import java.io.Serializable;
import java.util.List;
//Source: https://www.baeldung.com/jpa-one-to-one


@Entity
@Table(name="bidder")
public class BidderDetailsEntity implements Serializable {

    private static final long serialVersionUID = 1539092960638980319L;

    @Id
    @Column(name="id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private UserEntity user;


    //Do not apply cascading deletes!
    @OneToMany(mappedBy="bidder",
            cascade=CascadeType.ALL)
    private List<BidEntity> bids;

    @Column
    private int rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<BidEntity> getBids() {
        return bids;
    }

    public void setBids(List<BidEntity> bids) {
        this.bids = bids;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
