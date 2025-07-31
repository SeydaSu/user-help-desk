package com.admin.exception;

public class StatusNotFoundException extends RuntimeException {
    private String message;

    public StatusNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
