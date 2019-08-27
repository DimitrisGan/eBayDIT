package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

public class BidderDto implements Serializable {

    private static final long serialVersionUID = 8343846144175432530L;

    private Integer id;
    private Integer rating;
    private String  country;
    private String  location;

    private UserDto user;
    private List<BidDto> bids;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<BidDto> getBids() {
        return bids;
    }

    public void setBids(List<BidDto> bids) {
        this.bids = bids;
    }
}
