package com.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriorityResponse {

    private Long id;
    private String name; // Örn: "LOW", "MEDIUM", "HIGH"

}
