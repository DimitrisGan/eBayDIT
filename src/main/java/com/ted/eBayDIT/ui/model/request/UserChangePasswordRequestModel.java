package com.ted.eBayDIT.ui.model.request;

public class UserChangePasswordRequestModel {

    private String currPassword;
    private String newPassword;

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
