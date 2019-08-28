package com.ted.eBayDIT.ui.model.response;


import com.ted.eBayDIT.dto.CategoryDto;

import java.util.List;

public class CategoriesResponse {


    private  int id;
    private String name;
    private int parentId;
    private int level;


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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
