package com.ticket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;
import com.ticket.service.ITicketQueryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TicketQueryController {

    private final ITicketQueryService ticketQueryService;

    @GetMapping("ticket/my")
    public List<TicketEntity> getTicketsByCurrentUser() {
        return ticketQueryService.getTicketsByCurrentUser();
    }


    @GetMapping("ticket/{id}")
    public TicketResponse getTicketById(@RequestBody Long id) {
        return ticketQueryService.getTicketById(id);
    }
   
}
