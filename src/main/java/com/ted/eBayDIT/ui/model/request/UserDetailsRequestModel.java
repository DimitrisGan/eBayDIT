package com.ted.eBayDIT.ui.model.request;


import com.ted.eBayDIT.dto.RoleDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//todo edw tha to peiraksw otan thelw na xwsw XML leitourgikothta
public class UserDetailsRequestModel {


    @NotNull(message = "username cannot be missing")
    private String username;

    @Size(min=8, max=16, message=" Password must be equal to or greater than 8 characters and less than 16 characters")
    private String password;

    @Size(min=2, message="First name should have at least 2 characters")
    private String firstName;

    @Size(min=2, message="Last name should have at least 2 characters")
    private String lastName;

    @Email
    private String email;


    private String phoneNumber;
    private String country;
    private String location;
    private String afm;

    private RoleDto role;

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
