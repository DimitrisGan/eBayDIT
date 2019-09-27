package com.ted.eBayDIT.entity;


import javax.persistence.*;
import java.io.Serializable;



//@Data
@Entity
@Table(name="location")
public class ItemLocationEntity implements Serializable {

    private static final long serialVersionUID = -3728615755351500682L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private  Long id;

    @Column
    private String latitude;
    @Column
    private String longitude;
    @Column
    private String text;

    @OneToOne(mappedBy = "location")
    private ItemEntity item;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
