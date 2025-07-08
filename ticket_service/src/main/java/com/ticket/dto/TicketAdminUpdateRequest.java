package com.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketAdminUpdateRequest {

    private Long status;

    private Long priority;
    
    private String response;

    private Long ticketId;
    
}
