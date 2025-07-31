package com.admin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.dto.TagRequest;
import com.admin.dto.TagResponse;

@FeignClient(name = "tag-service", path = "/api/v1")
public interface TagClient {

    @PostMapping("/tags")
    public TagResponse createTag(@RequestBody TagRequest request);


    @GetMapping("/tags")
    public List<TagEntity> getAllTags();
}
