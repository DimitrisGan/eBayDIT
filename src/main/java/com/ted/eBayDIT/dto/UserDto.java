package com.ted.eBayDIT.dto;

import java.io.Serializable;
import java.util.List;

/*
DTO is a pattern and it is implementation (POJO/POCO) independent.
DTO says, since each call to any remote interface is expensive, response to each call should bring as much data as possible.
So, if multiple requests are required to bring data for a particular task, data to be brought can be combined in a DTO so that only one request can bring all the required data.
Catalog of Patterns of Enterprise Application Architecture has more details.
DTO's are a fundamental concept, not outdated
Source: https://softwareengineering.stackexchange.com/questions/171457/what-is-the-point-of-using-dto-data-transfer-objects
*/

public class UserDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private int id;
    private String userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;

    private String phoneNumber;
    private String country;
    private String location;
    private String afm;

    private boolean verified;

    private String userRole;

    private int totalPages;


    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
