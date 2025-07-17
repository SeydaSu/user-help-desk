package com.project.controller;

import java.util.List;


import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.model.TagEntity;

public interface ITagController {
    
    TagResponse createTag(TagRequest tagRequest);

    List<TagEntity> getAllTags();

}
