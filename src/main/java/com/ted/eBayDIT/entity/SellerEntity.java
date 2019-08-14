package com.ted.eBayDIT.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Seller")
public class SellerEntity {

    private String UserID;

    private String Rating;

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
}
