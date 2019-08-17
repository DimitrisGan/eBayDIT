package com.ted.eBayDIT.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

//Source: https://www.baeldung.com/jpa-one-to-one

@Data

@Entity
@Table(name="seller")
public class SellerDetailsEntity {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn
    @MapsId
    private UserEntity user;

    //Do not apply
    //cascading deletes!
    @OneToMany(mappedBy="seller",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ItemEntity> items;

    @Column
    private Integer rating;



}