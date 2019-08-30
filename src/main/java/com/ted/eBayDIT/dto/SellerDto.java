package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

public class SellerDto implements Serializable {
    private static final long serialVersionUID = 3752263619169370266L;

    private int id;
    private int rating;

    private UserDto user;
    private List<ItemDto> items;




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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
