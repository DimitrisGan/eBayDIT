package com.ted.eBayDIT.ui.model.response;

import com.ted.eBayDIT.dto.UserDto;

public class BidderResponseModel {

    private Integer id;
    private Integer rating;

    private UserDetailsResponseModel user;

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

    public UserDetailsResponseModel getUser() {
        return user;
    }

    public void setUser(UserDetailsResponseModel user) {
        this.user = user;
    }
}
