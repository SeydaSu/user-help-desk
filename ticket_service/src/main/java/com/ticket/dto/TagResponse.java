package com.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TagResponse {

    private Long id;

    @NotBlank(message = "Tag name cannot be empty")
    private String name;

    private String createdBy;

    private LocalDateTime createdAt;
}
