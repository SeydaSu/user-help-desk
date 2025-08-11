package com.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.TagEntity;
import com.admin.dto.PriorityRequest;
import com.admin.dto.PriorityResponse;
import com.admin.dto.StatusRequest;
import com.admin.dto.StatusResponse;
import com.admin.dto.TagRequest;
import com.admin.dto.TagResponse;
import com.admin.model.PriorityEntity;
import com.admin.model.StatusEntity;
import com.admin.service.IAdminTicketAssignService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/admin/ticket")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminTicketAssignController {

    private final IAdminTicketAssignService adminTicketAssignService;

    @GetMapping("/priorities")
    public List<PriorityEntity> getAllPriorities(){
        return adminTicketAssignService.getAllPriorities();
    }

    @PostMapping("/priorities")
    public PriorityResponse createPriority(@RequestBody PriorityRequest request){
        return adminTicketAssignService.createPriority(request);
    }

    @GetMapping("/tags")
    public List<TagEntity> getAllTags(){
        return adminTicketAssignService.getAllTags();
    }

    @PostMapping("/tags")
    public TagResponse createTag(@RequestBody TagRequest request){
        return adminTicketAssignService.createTag(request);
    }

    @GetMapping("/statuses")
    public List<StatusEntity> getAllStatuses(){
        return adminTicketAssignService.getAllStatuses();
    }

    @PostMapping("/statuses")
    public StatusResponse createStatus(@RequestBody StatusRequest request){
        return adminTicketAssignService.createStatus(request);
    }


}
