package com.project.service;

import java.util.List;

import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.model.TagEntity;

public interface ITagService {

    TagResponse createTag(TagRequest request);

    List<TagEntity> getAllTags();
    
}
