package com.ticket.dto;

import com.ticket.model.TicketPriorityEntity;
import com.ticket.model.TicketStatusEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    
    private TicketPriorityEntity priority;

    private TicketStatusEntity status;

    private Long userId;


    

   
}
