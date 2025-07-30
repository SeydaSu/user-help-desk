package com.admin.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import com.admin.dto.TicketAdminUpdateRequest;
import com.admin.dto.TicketResponse;

@FeignClient(name = "ticket-service", path = "/api/v1/ticket")
public interface TicketClient {
    
    @PutMapping("/{id}")
    TicketResponse respondToTicket(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request);

    @PutMapping("/{id}/priority")
    TicketResponse updatePriority(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request);

    @PutMapping("/{id}/status")
    TicketResponse updateStatus(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request);

    @PutMapping("/{id}/tag")
    TicketResponse updateTag(@PathVariable("id") Long id, @RequestBody TicketAdminUpdateRequest request);

    @GetMapping("/admin/list")
    List<TicketResponse> getAllTicketsForAdmin();

    @GetMapping("/{id}")
    TicketResponse getTicketById(@PathVariable("id") Long id);
}
