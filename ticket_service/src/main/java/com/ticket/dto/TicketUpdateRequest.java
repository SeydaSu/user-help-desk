package com.ticket.dto;

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
    private Long priorityId;
    private Long statusId;
    private Long tagId;

    // Additional fields can be added as needed
    
}
