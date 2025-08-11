package com.ticket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.TicketAdminUpdateRequest;
import com.ticket.dto.TicketResponse;
import com.ticket.model.TicketEntity;
import com.ticket.service.ITicketAdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketAdminController {

    private final ITicketAdminService ticketAdminService;

    @PutMapping("/{id}")
    public TicketResponse respondToTicket(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request) {
        return ticketAdminService.respondToTicket(id, request);
    }

    @PutMapping("/{id}/priority")
    public TicketResponse updatePriority(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request) {
        return ticketAdminService.updatePriority(id, request);
    }

    @PutMapping("/{id}/status")
    public TicketResponse updateStatus(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request) {
        return ticketAdminService.updateStatus(id, request);
    }

    @PutMapping("/{id}/tag")
    public TicketResponse updateTag(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request) {
        return ticketAdminService.updateTag(id, request);
    }

    @GetMapping ("/admin/tickets")
    public List<TicketEntity> getAllTicketsForAdmin() {
        return ticketAdminService.getAllTicketsForAdmin();
    }



}
