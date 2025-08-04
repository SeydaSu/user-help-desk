package com.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.admin.service.IAdminTicketListService;
import com.admin.dto.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/ticket")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminTicketListController {

    private final IAdminTicketListService adminTicketListService;

    @GetMapping
    public List<TicketResponse> getAllTicketsForAdmin() {
        return adminTicketListService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable("id") Long id) {
        return adminTicketListService.getTicketById(id);
    }
}