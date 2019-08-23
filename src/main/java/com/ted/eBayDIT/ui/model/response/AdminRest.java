package com.ted.eBayDIT.ui.model.response;

import java.util.List;

public class AdminRest {

    private List<UserRest> users;
    private int totalPages;
    private int totalUsers;

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

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
