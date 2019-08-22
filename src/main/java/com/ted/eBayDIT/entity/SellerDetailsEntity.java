package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.util.List;

//Source: https://www.baeldung.com/jpa-one-to-one

//@Data

@Entity
@Table(name="seller")
public class SellerDetailsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_username",nullable = false)
//    @MapsId
    private UserEntity user;

    //Do not apply
    //cascading deletes!
    @OneToMany(mappedBy="seller",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ItemEntity> items;

    @Column
    private Integer rating;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}