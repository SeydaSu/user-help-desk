package com.admin.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.dto.TicketAdminUpdateRequest;
import com.admin.dto.TicketResponse;

public interface IAdminTicketResponseService {
    

    public TicketResponse respondToTicket(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request) ;


    public TicketResponse updateTicketPriority(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request);


    public TicketResponse updateTicketStatus(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request);


    public TicketResponse updateTicketTag(@PathVariable Long id, @RequestBody TicketAdminUpdateRequest request);


}
