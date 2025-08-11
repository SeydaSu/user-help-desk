package com.ticket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ticket.dto.TagResponse;


@FeignClient(name = "tag-service")
public interface TagClient {
    @GetMapping("/api/v1/tags/{id}")
    TagResponse getTagById(@PathVariable Long id);
}
