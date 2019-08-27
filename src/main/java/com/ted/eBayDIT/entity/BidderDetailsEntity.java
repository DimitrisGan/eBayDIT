package com.ted.eBayDIT.entity;

import javax.persistence.*;


import java.util.List;
//Source: https://www.baeldung.com/jpa-one-to-one


@Entity
@Table(name="bidder")
public class BidderDetailsEntity {

    @Id
    @Column(name="id")
    private int id;

//    @JoinColumn(name = "ID")
    @OneToOne(/*fetch = FetchType.LAZY,*/cascade = CascadeType.ALL)
    @MapsId
    private UserEntity user;


    //Do not apply cascading deletes!
    @OneToMany(mappedBy="bidder",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<BidEntity> bids;

    @Column
    private int rating;
//    @Column
//    private String country;
//    @Column
//    private String location;

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

//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
}
