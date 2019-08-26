package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//Source: https://www.baeldung.com/jpa-one-to-one

//@Data

@Entity
@Table(name="seller")
public class SellerDetailsEntity implements Serializable {

    private static final long serialVersionUID = -2570021379769487425L;

//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private int id;


//    @OneToOne/*(cascade = CascadeType.ALL)*/
////    @JoinColumn(name="user_username",nullable = false)
//    @MapsId
//    public String username ;


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @MapsId
//    @JoinColumn(name = "RequestID")

    private UserEntity user;

    //Do not apply cascading deletes!
    @OneToMany(mappedBy="seller",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ItemEntity> items;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}