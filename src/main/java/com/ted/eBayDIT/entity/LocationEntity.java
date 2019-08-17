package com.ted.eBayDIT.entity;

import lombok.Data;

import javax.persistence.*;


//<Location Latitude="43.108241" Longitude="-88.48935">Oconomowoc, WI</Location>
//{
//        "@Latitude": "37.680181",
//        "@Longitude": "-121.921498",
//        "#text": "Hayward, CA"
//}
@Data
@Entity
@Table(name="Location")
public class LocationEntity {

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


}
