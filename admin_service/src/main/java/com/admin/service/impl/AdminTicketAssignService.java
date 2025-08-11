package com.admin.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.admin.client.TagClient;
import com.admin.client.TagEntity;
import com.admin.dto.PriorityRequest;
import com.admin.dto.PriorityResponse;
import com.admin.dto.StatusRequest;
import com.admin.dto.StatusResponse;
import com.admin.dto.TagRequest;
import com.admin.dto.TagResponse;
import com.admin.exception.PriorityNotFoundException;
import com.admin.exception.StatusNotFoundException;
import com.admin.exception.TagNotFoundException;
import com.admin.kafka.KafkaProducerService;
import com.admin.model.PriorityEntity;
import com.admin.model.StatusEntity;
import com.admin.repository.PriorityRepository;
import com.admin.repository.StatusRepository;
import com.admin.service.IAdminTicketAssignService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminTicketAssignService implements IAdminTicketAssignService {

    private final TagClient tagClient;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public List<TagEntity> getAllTags() {
        List<TagEntity> tags = tagClient.getAllTags();
        return tags;
    }

    @Override
    public TagResponse createTag(TagRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new TagNotFoundException("Tag name cannot be empty");
        }
        TagResponse tag = tagClient.createTag(request);

        String eventJson = """
                    {
                        "tagId": "%s",
                        "tagName": "%s",
                        "eventType": "TAG_CREATED",
                        "timestamp": "%s"
                    }
                """.formatted(
                tag.getId(),
                tag.getName(),
                LocalDateTime.now());

        kafkaProducerService.sendTagCreatedEvent(eventJson);

        return tag;
    }

    @Override
    public List<PriorityEntity> getAllPriorities() {
        return priorityRepository.findAll();
    }

    @Override
    public PriorityResponse createPriority(PriorityRequest request) {
        String createdBy = "unknown";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            createdBy = authentication.getName();
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new PriorityNotFoundException("Priority name cannot be empty");
        }

        if (priorityRepository.findByName(request.getName()) != null) {
            throw new PriorityNotFoundException("Priority already exists");
        }

        PriorityEntity priorityEntity = PriorityEntity.builder()
                .name(request.getName())
                .createdBy(createdBy)
                .build();

        PriorityEntity savedTag = priorityRepository.save(priorityEntity);

        String eventJson = """
                    {
                        "priorityId": "%s",
                        "priorityName": "%s",
                        "eventType": "TAG_CREATED",
                        "timestamp": "%s"
                    }
                """.formatted(
                priorityEntity.getId(),
                priorityEntity.getName(),
                LocalDateTime.now());

        kafkaProducerService.sendPriorityCreatedvent(eventJson);

        return PriorityResponse.builder()
                .name(savedTag.getName())
                .createdBy(savedTag.getCreatedBy())
                .build();
    }

    @Override
    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public StatusResponse createStatus(StatusRequest request) {
        String createdBy = "unknown";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            createdBy = authentication.getName();
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new StatusNotFoundException("Status name cannot be empty");
        }

        if (priorityRepository.findByName(request.getName()) != null) {
            throw new StatusNotFoundException("Status already exists");
        }

        StatusEntity statusEntity = StatusEntity.builder()
                .name(request.getName())
                .createdBy(createdBy)
                .build();

        StatusEntity savedTag = statusRepository.save(statusEntity);

        String eventJson = """
                    {
                        "statusId": "%s",
                        "statusName": "%s",
                        "eventType": "TAG_CREATED",
                        "timestamp": "%s"
                    }
                """.formatted(
                statusEntity.getId(),
                statusEntity.getName(),
                LocalDateTime.now());

        kafkaProducerService.sendTagCreatedEvent(eventJson);

        return StatusResponse.builder()
                .name(savedTag.getName())
                .createdBy(savedTag.getCreatedBy())
                .build();
    }

}
