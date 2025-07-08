package com.ticket.dto;

import com.ticket.model.TicketStatusEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketAdminUpdateRequest {

    private TicketStatusEntity status;

    private TicketStatusEntity priority;
    
    private String response;

    private Long ticketId;
    
}
