package com.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.admin.client.TagEntity;
import com.admin.dto.PriorityRequest;
import com.admin.dto.PriorityResponse;
import com.admin.dto.StatusRequest;
import com.admin.dto.StatusResponse;
import com.admin.dto.TagRequest;
import com.admin.dto.TagResponse;
import com.admin.model.PriorityEntity;
import com.admin.model.StatusEntity;

public interface IAdminTicketAssignService {


    public List<PriorityEntity> getAllPriorities();

    public PriorityResponse createPriority(@RequestBody PriorityRequest request);

    public List<TagEntity> getAllTags();

    public TagResponse createTag(@RequestBody TagRequest request);

    public List<StatusEntity> getAllStatuses();

    public StatusResponse createStatus(@RequestBody StatusRequest request);


    
}
