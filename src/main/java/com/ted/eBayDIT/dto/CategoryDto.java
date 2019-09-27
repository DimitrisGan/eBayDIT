package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    private static final long serialVersionUID = 4164368160281593017L;

    private  int id;
    private String name;

    private int parentId;
    private int level;


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


}
