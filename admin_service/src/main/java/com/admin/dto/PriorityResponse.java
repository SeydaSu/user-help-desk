package com.admin.dto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PriorityResponse {

    private Long id;

    private String name;

    private String createdBy;

    private LocalDateTime createdAt;
}
