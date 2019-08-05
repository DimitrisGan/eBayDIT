package com.ted.eBayDIT.entity;

public enum RoleName {

    ADMIN("ADMIN"),
    USER("USER");


    private String RoleName;


    RoleName(String roleName) {
        RoleName = roleName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
