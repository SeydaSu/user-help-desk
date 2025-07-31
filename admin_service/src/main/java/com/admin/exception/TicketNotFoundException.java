package com.admin.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TicketNotFoundException extends RuntimeException {
    private final String message;
}
