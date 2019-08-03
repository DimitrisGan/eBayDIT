package com.ted.eBayDIT.ui.model.response;


import java.util.Date;

public class ErrorDetailsModel {

    private Date timestamp;
    private String message;
    private String details;

        public ErrorDetailsModel() {}

        public ErrorDetailsModel(Date timestamp, String message, String details)
        {
            this.message = message;
            this.timestamp = timestamp;
            this.details = details;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
