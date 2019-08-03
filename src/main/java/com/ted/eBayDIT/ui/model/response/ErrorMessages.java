package com.ted.eBayDIT.ui.model.response;


// Source: http://appsdeveloperblog.com/exception-handling-restful-web-service/
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field! Please give all the required fields"),
    RECORD_ALREADY_EXISTS("Record already exists!"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    AUTHENTICATION_FAILED("Authentication failed!"),
    COULD_NOT_UPDATE_RECORD("Could not update record!"),
    COULD_NOT_DELETE_RECORD("Could not delete record!"),

    INTERNAL_SERVER_ERROR("Internal server error!");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
