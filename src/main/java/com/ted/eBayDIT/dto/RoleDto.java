package com.ted.eBayDIT.dto;

import java.io.Serializable;

public class RoleDto implements Serializable {


    private static final long serialVersionUID = -7414254535801645787L;

    private int id;
    private String userRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
