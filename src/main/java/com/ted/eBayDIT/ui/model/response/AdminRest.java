package com.ted.eBayDIT.ui.model.response;

import java.util.List;

public class AdminRest {

    private List<UserDetailsResponseModel> users;
    private int totalPages;
    private int totalUsers;

    public List<UserDetailsResponseModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserDetailsResponseModel> users) {
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
