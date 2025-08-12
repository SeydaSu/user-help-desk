package com.project.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.exception.TagNotFoundException;
import com.project.model.TagEntity;
import com.project.repository.TagRepository;
import com.project.service.ITagService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;


    @Override
    public TagResponse createTag(TagRequest request) {
        String createdBy = "unknown";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            createdBy = authentication.getName();
        }

        // Eğer name boşsa veya zaten varsa, hata fırlat
        if (request == null ||request.getName() == null || request.getName().isBlank()) {
            throw new TagNotFoundException("Tag name cannot be empty");
        }

        if (tagRepository.findByName(request.getName()) != null) {
            throw new TagNotFoundException("Tag already exists");
        }

        TagEntity tag = TagEntity.builder()
                .name(request.getName())
                .createdBy(createdBy)
                .build();

        TagEntity savedTag = tagRepository.save(tag);

        return TagResponse.builder()
                .id(savedTag.getId())
                .name(savedTag.getName())
                .createdAt(savedTag.getCreatedAt())
                .createdBy(savedTag.getCreatedBy())
                .build();
    }



    @Override
    public List<TagEntity> getAllTags() {
            return tagRepository.findAll();
    }
        
}
