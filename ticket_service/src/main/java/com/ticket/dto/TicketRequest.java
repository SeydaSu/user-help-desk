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
public class TicketRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    
    private Long priority;

    private Long status;

    private Long userId;


    

   
}
