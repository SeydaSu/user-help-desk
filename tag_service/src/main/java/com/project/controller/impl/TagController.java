package com.project.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.controller.ITagController;
import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.model.TagEntity;
import com.project.service.ITagService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TagController implements ITagController {

    private final ITagService tagService;

    @PostMapping("/tags")
    public TagResponse createTag(@RequestBody TagRequest request) {
        return tagService.createTag(request);
    }


    @GetMapping("/tags")
    public List<TagEntity> getAllTags() {
        return tagService.getAllTags();
    }
    

    

    
    
}
