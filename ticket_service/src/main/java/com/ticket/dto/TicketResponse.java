package com.ticket.dto;

import java.time.LocalDateTime;

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
public class TicketResponse {

    
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    
    private TicketPriorityEntity priority;

    private TicketStatusEntity status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private Long userId;
    
    private Long tagId;

    

}
