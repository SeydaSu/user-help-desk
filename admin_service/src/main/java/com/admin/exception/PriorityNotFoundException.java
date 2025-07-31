package com.admin.exception;

public class PriorityNotFoundException extends RuntimeException {
    private String message;

    public PriorityNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
