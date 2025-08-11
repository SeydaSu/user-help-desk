package com.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketAdminUpdateRequest {
    
    private Long ticketId;

    private Long statusId;

    private Long priorityId;
    
    private String response;

    private Long tagId;
    
    private Long userId;

    
    
}
