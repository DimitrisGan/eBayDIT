package com.ted.eBayDIT.ui.model.response;

public class BidderResponseModel {

    private Integer id;
    private Integer rating;

    private UserShortInfoResponseModel user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public UserShortInfoResponseModel getUser() {
        return user;
    }

    public void setUser(UserShortInfoResponseModel user) {
        this.user = user;
    }
}
