package com.ticket.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "admin-service")
public interface AdminClient {

    @GetMapping("/api/v1/admin/priorities")
    List<PriorityEntity> getAllPriorities();

    @GetMapping("/api/v1/admin/statuses")
    List<StatusEntity> getAllStatuses();

    @GetMapping("/api/v1/admin/tags")
    List<TagEntity> getAllTags();
}
