package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4164368160281593017L;

    private  int id;
    private String name;
    private List<ItemDto> itemDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemDto> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDto> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
