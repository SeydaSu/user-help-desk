package com.ticket.dto;

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
    
    private String priority;

    private String status;

    private String createdAt;

    private String updatedAt;

    private String createdBy;

    private Long userId;
    
    private Long tagId;

    

}
