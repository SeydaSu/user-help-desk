package com.project.exception;

public class TagNotFoundException extends RuntimeException {
    private String message;

    public TagNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
