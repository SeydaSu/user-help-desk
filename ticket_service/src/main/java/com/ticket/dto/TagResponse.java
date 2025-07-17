package com.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {

    private Long id;

    @NotBlank(message = "Tag name cannot be empty")
    private String name;

    private String createdBy;

    private LocalDateTime createdAt;
}
