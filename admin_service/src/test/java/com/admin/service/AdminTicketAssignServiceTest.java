package com.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.admin.client.TagClient;
import com.admin.client.TagEntity;
import com.admin.dto.PriorityRequest;
import com.admin.dto.PriorityResponse;
import com.admin.dto.StatusRequest;
import com.admin.dto.StatusResponse;
import com.admin.dto.TagRequest;
import com.admin.dto.TagResponse;
import com.admin.exception.TagNotFoundException;
import com.admin.model.PriorityEntity;
import com.admin.model.StatusEntity;
import com.admin.repository.PriorityRepository;
import com.admin.repository.StatusRepository;
import com.admin.service.impl.AdminTicketAssignService;

@ExtendWith(MockitoExtension.class)
public class AdminTicketAssignServiceTest {

    @Mock
    private TagClient tagClient;
    @Mock
    private PriorityRepository priorityRepository;
    @Mock
    private StatusRepository statusRepository;

    @Mock
    private AdminTicketAssignService adminTicketAssignService;

    @BeforeEach
    void setUp() {
        tagClient = mock(TagClient.class);
        adminTicketAssignService = new AdminTicketAssignService(tagClient,priorityRepository,statusRepository);
    }

    @Test
    public void givenValidPriorityRequest_whenCreatePriority_thenReturnsSavedPriorityResponse() {
        PriorityEntity priority = new PriorityEntity();
        priority.setName("test-priority");
        priority.setCreatedBy("test-user");

        when(priorityRepository.save(any(PriorityEntity.class))).thenReturn(priority);

        PriorityResponse response = adminTicketAssignService.createPriority(new PriorityRequest("test-priority"));

        assertEquals("test-priority", response.getName());
        assertEquals("test-user", response.getCreatedBy());
        verify(priorityRepository, times(1)).save(any(PriorityEntity.class));


    }



    @Test
    public void givenNullOrEmptyPriorityName_whenCreatePriority_thenThrowsPriorityNotFoundException() {
        PriorityRepository repo = mock(PriorityRepository.class);

        PriorityRequest emptyRequest = new PriorityRequest("");
        PriorityRequest nullRequest = new PriorityRequest(); // setter varsa
        nullRequest.setName(null);


        assertThrows(RuntimeException.class, () -> adminTicketAssignService.createPriority(emptyRequest));
        assertThrows(RuntimeException.class, () -> adminTicketAssignService.createPriority(nullRequest));

        verify(repo, never()).save(any(PriorityEntity.class));
    }


    @Test 
    public void givenExistingPriorities_whenGetAllPriorities_thenReturnsPriorityResponseList() {
        PriorityEntity priority1 = new PriorityEntity();
        priority1.setName("test-priority");
        priority1.setCreatedBy("test-user");

        PriorityEntity priority2 = new PriorityEntity();
        priority2.setName("another-priority");
        priority2.setCreatedBy("another-user");
        
        List<PriorityEntity> priorities = Arrays.asList(priority1, priority2);

        when(priorityRepository.findAll()).thenReturn(priorities);

        List<PriorityEntity> result = adminTicketAssignService.getAllPriorities();

        assertEquals(2, result.size());
        assertEquals("test-priority", result.get(0).getName());
        assertEquals("another-priority", result.get(1).getName());
        verify(priorityRepository, times(1)).findAll();
    }

    @Test
    public void givenNoPriorityExist_whenGetAllTags_thenReturnsEmptyList() {
        when(priorityRepository.findAll()).thenReturn(Arrays.asList());

        List<PriorityEntity> result = adminTicketAssignService.getAllPriorities();

        assertEquals(0, result.size());
        verify(priorityRepository, times(1)).findAll();
    }


    @Test
    public void givenValidStatusRequest_whenCreateStatus_thenReturnsSavedStatusResponse() {
        StatusEntity status = new StatusEntity();
        status.setName("test-status");
        status.setCreatedBy("test-user");

        when(statusRepository.save(any(StatusEntity.class))).thenReturn(status);

        StatusResponse response = adminTicketAssignService.createStatus(new StatusRequest("test-status"));

        assertEquals("test-status", response.getName());
        assertEquals("test-user", response.getCreatedBy());
        verify(statusRepository, times(1)).save(any(StatusEntity.class));


    }



    @Test
    public void givenNullOrEmptyStatusName_whenCreateStatus_thenThrowsStatusNotFoundException() {
        StatusRepository repo = mock(StatusRepository.class);

        StatusRequest emptyRequest = new StatusRequest("");
        StatusRequest nullRequest = new StatusRequest(); // setter varsa
        nullRequest.setName(null);


        assertThrows(RuntimeException.class, () -> adminTicketAssignService.createStatus(emptyRequest));
        assertThrows(RuntimeException.class, () -> adminTicketAssignService.createStatus(nullRequest));

        verify(repo, never()).save(any(StatusEntity.class));
    }


    @Test 
    public void givenExistingStatuses_whenGetAllStatuses_thenReturnsStatusResponseList() {
        StatusEntity status1 = new StatusEntity();
        status1.setName("test-status");
        status1.setCreatedBy("test-user");

        StatusEntity status2 = new StatusEntity();
        status2.setName("another-status");
        status2.setCreatedBy("another-user");

        when(statusRepository.findAll()).thenReturn(Arrays.asList(status1, status2));

        List<StatusEntity> result = adminTicketAssignService.getAllStatuses();

        assertEquals(2, result.size());
        assertEquals("test-status", result.get(0).getName());
        assertEquals("another-status", result.get(1).getName());
        verify(statusRepository, times(1)).findAll();
    }

    @Test
    public void givenNoStatusExist_whenGetAllStatuses_thenReturnsEmptyList() {
        when(statusRepository.findAll()).thenReturn(Arrays.asList());

        List<StatusEntity> result = adminTicketAssignService.getAllStatuses();

        assertEquals(0, result.size());
        verify(statusRepository, times(1)).findAll();
    }


    @Test
    public void givenValidTagRequest_whenCreateTag_thenReturnsSavedTagResponse() {
        TagRequest request = new TagRequest("Important");
        TagResponse expectedResponse = new TagResponse(1L, "Important","user",LocalDateTime.now());

        when(tagClient.createTag(request)).thenReturn(expectedResponse);

        TagResponse actualResponse = adminTicketAssignService.createTag(request);

        assertEquals("Important", actualResponse.getName());
        verify(tagClient).createTag(request);
    }

    @Test
    public void givenNullOrEmptyTagName_whenCreateTag_thenThrowsTagNotFoundException() {
        TagRequest request = new TagRequest(null);

        assertThrows(TagNotFoundException.class, () -> {
            adminTicketAssignService.createTag(request);
        });
    }





    @Test 
    public void givenExistingTags_whenGetAllTags_thenReturnstagResponseList() {
        TagEntity tag1 = new TagEntity();
        tag1.setName("test-tag");
        tag1.setCreatedAt(LocalDateTime.now());
        tag1.setCreatedBy("test-user");

        TagEntity tag2 = new TagEntity();
        tag2.setName("another-tag");
        tag2.setCreatedAt(LocalDateTime.now());
        tag2.setCreatedBy("another-user");

        List<TagEntity> mockTags = List.of(tag1, tag2);
        when(tagClient.getAllTags()).thenReturn(mockTags);

        List<TagEntity> result = adminTicketAssignService.getAllTags();

        assertEquals(2, result.size());
        assertEquals("test-tag", result.get(0).getName());
        verify(tagClient, times(1)).getAllTags();
    }


    @Test
    public void givenNoTagExist_whenGetAllTags_thenReturnsEmptyList() {
        List<TagEntity> mockTags = Collections.EMPTY_LIST;
        when(tagClient.getAllTags()).thenReturn(mockTags);
        List<TagEntity> result = adminTicketAssignService.getAllTags();
        
        assertEquals(0, result.size());
        verify(tagClient, times(1)).getAllTags();
    }
}
