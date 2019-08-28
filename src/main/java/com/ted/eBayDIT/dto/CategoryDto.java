package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4164368160281593017L;

    private  int id;
    private String name;

    private int parent_id;
    private int level;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //    private List<ItemDto> itemDetails;

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

//    public List<ItemDto> getItemDetails() {
//        return itemDetails;
//    }
//
//    public void setItemDetails(List<ItemDto> itemDetails) {
//        this.itemDetails = itemDetails;
//    }
}
