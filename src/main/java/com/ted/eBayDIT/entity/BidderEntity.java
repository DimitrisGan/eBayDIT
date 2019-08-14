package com.ted.eBayDIT.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Bidder")
public class BidderEntity {


    private String UserID;

    private String Rating;

    private String Country;

    private String Location;


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
