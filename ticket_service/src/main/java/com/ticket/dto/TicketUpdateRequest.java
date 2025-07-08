package com.ticket.dto;

import com.ticket.model.TicketPriorityEntity;
import com.ticket.model.TicketStatusEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateRequest {

    private String title;
    private String description;
    private TicketPriorityEntity priority;
    private TicketStatusEntity status;
    private Long tagId;

    // Additional fields can be added as needed
    
}
