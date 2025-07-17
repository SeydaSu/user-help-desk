package com.project.tag_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.exception.TagNotFoundException;
import com.project.model.TagEntity;
import com.project.repository.TagRepository;
import com.project.service.impl.TagService;




class TagServiceTest {
    // givenValidTagRequest_whenCreateTag_thenReturnsSavedTagResponse

    // givenNullOrEmptyTagName_whenCreateTag_thenThrowsInvalidInputException

    // givenExistingTags_whenGetAllTags_thenReturnsTagResponseList

    // givenNoTagsExist_whenGetAllTags_thenReturnsEmptyList

    @Test
    public void givenValidTagRequest_whenCreateTag_thenReturnsSavedTagResponse() {
        TagEntity tag = new TagEntity();
        tag.setName("test-tag");
        tag.setCreatedAt(LocalDateTime.now());
        tag.setCreatedBy("test-user");

        TagRepository repo = mock(TagRepository.class);
        when(repo.save(any(TagEntity.class))).thenReturn(tag);

        TagService service = new TagService(repo);
        TagResponse response = service.createTag(new TagRequest("test-tag"));

        assertEquals("test-tag", response.getName());
        assertEquals("test-user", response.getCreatedBy());
        verify(repo, times(1)).save(any(TagEntity.class));


    }



    @Test
    public void givenNullOrEmptyTagName_whenCreateTag_thenThrowsTagNotFoundException() {
        TagRepository repo = mock(TagRepository.class);
        TagService service = new TagService(repo);

        TagRequest emptyRequest = new TagRequest("");
        TagRequest nullRequest = new TagRequest(); // setter varsa
        nullRequest.setName(null);


        assertThrows(TagNotFoundException.class, () -> service.createTag(emptyRequest));
        assertThrows(TagNotFoundException.class, () -> service.createTag(nullRequest));

        verify(repo, never()).save(any(TagEntity.class));
    }


    @Test 
    public void givenExistingTags_whenGetAllTags_thenReturnsTagResponseList() {
        TagEntity tag1 = new TagEntity();
        tag1.setName("test-tag");
        tag1.setCreatedAt(LocalDateTime.now());
        tag1.setCreatedBy("test-user");

        TagEntity tag2 = new TagEntity();
        tag2.setName("another-tag");
        tag2.setCreatedAt(LocalDateTime.now());
        tag2.setCreatedBy("another-user");

        TagRepository repo = mock(TagRepository.class);
        when(repo.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        TagService service = new TagService(repo);
        List<TagEntity> result = service.getAllTags();

        assertEquals(2, result.size());
        assertEquals("test-tag", result.get(0).getName());
        assertEquals("another-tag", result.get(1).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void givenNoTagsExist_whenGetAllTags_thenReturnsEmptyList() {
        TagRepository repo = mock(TagRepository.class);
        when(repo.findAll()).thenReturn(Arrays.asList());

        TagService service = new TagService(repo);
        List<TagEntity> result = service.getAllTags();

        assertEquals(0, result.size());
        verify(repo, times(1)).findAll();
    }
}