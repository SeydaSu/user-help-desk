package com.admin.service;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import com.admin.dto.TicketResponse;

public interface IAdminTicketListService {
    
    public List<TicketResponse> getAllTickets();

    public TicketResponse getTicketById(@PathVariable Long id);
    
}
