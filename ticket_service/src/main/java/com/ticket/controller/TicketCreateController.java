package com.ticket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.TicketRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.service.ITicketCreateService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 
public class TicketCreateController {

    private final ITicketCreateService ticketCreateService;

    @PostMapping("/ticket")
    public TicketResponse createTicket(@RequestBody TicketRequest request) {
        return ticketCreateService.createTicket(request);
    }
    

}
