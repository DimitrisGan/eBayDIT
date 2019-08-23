package com.ted.eBayDIT.ui.model.response;

import java.util.List;

public class AdminRest {

    private List<UserRest> users;
    private int totalPages;

    public List<UserRest> getUsers() {
        return users;
    }

    public void setUsers(List<UserRest> users) {
        this.users = users;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
