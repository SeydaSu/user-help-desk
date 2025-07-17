package com.ticket.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ticket.dto.PriorityResponse;
import com.ticket.dto.StatusResponse;

@FeignClient(name = "admin-service")
public interface AdminClient {

    @GetMapping("/api/v1/priorities/{id}")
    PriorityResponse getPriorityById(@PathVariable Long id);

    @GetMapping("/api/v1/statuses/{id}")
    StatusResponse getStatusById(@PathVariable Long id);
}
