package com.ted.eBayDIT.dto;

import java.io.Serializable;

public class RoleDto implements Serializable {


    private static final long serialVersionUID = 4L;

    private int id;
    private String userRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return userRole;
    }

    public void setName(String name) {
        this.userRole = name;
    }
}
