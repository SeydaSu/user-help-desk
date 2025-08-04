package com.admin.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.admin.dto.TicketAdminUpdateRequest;
import com.admin.dto.TicketResponse;
import com.admin.service.IAdminTicketResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/ticket")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminTicketRespondController {

    private final IAdminTicketResponseService adminTicketResponseService;
    

    @PutMapping("/{id}")
    TicketResponse respondToTicket(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request){
        return adminTicketResponseService.respondToTicket(id, request);
    }

    @PutMapping("/{id}/priority")
    TicketResponse updatePriority(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request){
        return adminTicketResponseService.updateTicketPriority(id, request);
    }
    

    @PutMapping("/{id}/status")
    TicketResponse updateStatus(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request){
        return adminTicketResponseService.updateTicketStatus(id, request);
    }

    @PutMapping("/{id}/tag")
    TicketResponse updateTag(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request){
        return adminTicketResponseService.updateTicketTag(id, request);
    }
}
