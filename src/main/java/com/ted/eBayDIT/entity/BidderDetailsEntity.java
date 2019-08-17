package com.ted.eBayDIT.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.List;
//Source: https://www.baeldung.com/jpa-one-to-one

@Data

@Entity
@Table(name="bidder")
public class BidderDetailsEntity {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn
    @MapsId
    private UserEntity user;


    //Do not apply
    //cascading deletes!
    @OneToMany(mappedBy="bidder",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    List<BidEntity> bids;

    @Column
    private Integer rating;
    @Column
    private String country;
    @Column
    private String location;


}
