package com.project.tag_service.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.controller.impl.TagController;
import com.project.dto.TagRequest;
import com.project.dto.TagResponse;
import com.project.exception.GlobalExceptionHandler;
import com.project.exception.TagNotFoundException;
import com.project.model.TagEntity;
import com.project.service.ITagService;

import org.springframework.http.MediaType;


@WebMvcTest(controllers = TagController.class, excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
    org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})

@Import(GlobalExceptionHandler.class)

@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenValidTagRequest_whenCreateTag_thenReturnsSavedTagResponse() throws Exception {
        TagRequest request = new TagRequest("tag");
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        TagResponse response = TagResponse.builder()
                .name("tag")
                .createdBy("user")
                .createdAt(fixedDateTime)
                .build();

        when(tagService.createTag(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tag"))
                .andExpect(jsonPath("$.createdBy").value("user"))
                .andExpect(jsonPath("$.createdAt").value("2024-01-01T12:00:00"));
    }

        


    @Test
    public void givenNullOrEmptyTagName_whenCreateTag_thenThrowsTagNotFoundException() throws JsonProcessingException, Exception {
        

        TagRequest nullRequest = new TagRequest(null);
        TagRequest emptyRequest = new TagRequest("");

         // Geçersiz inputlar için özel hata fırlat
        when(tagService.createTag(Mockito.argThat(req ->
                req.getName() == null || req.getName().isBlank())))
                .thenThrow(new TagNotFoundException("Tag name cannot be empty"));

        mockMvc.perform(post("/api/v1/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(nullRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Tag name cannot be empty"));

        mockMvc.perform(post("/api/v1/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(emptyRequest)))
            .andExpect(status().isBadRequest())
        .andExpect(content().string("Tag name cannot be empty"));

     }

    



    @Test
    public void givenExistingTags_whenGetAllTags_thenReturnsTagResponseList() throws Exception {
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 1, 1, 12, 0);

        TagEntity tag1 = TagEntity.builder()
            .name("tag1")
            .createdBy("user1")
            .createdAt(fixedDateTime)
            .build();

        TagEntity tag2 = TagEntity.builder()
            .name("tag2")
            .createdBy("user2")
            .createdAt(fixedDateTime)
            .build();

        when(tagService.getAllTags()).thenReturn(List.of(tag1, tag2));

        mockMvc.perform(get("/api/v1/tags"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("tag1"))
            .andExpect(jsonPath("$[0].createdBy").value("user1"))
            .andExpect(jsonPath("$[0].createdAt").value("2024-01-01T12:00:00"))
            .andExpect(jsonPath("$[1].name").value("tag2"))
            .andExpect(jsonPath("$[1].createdBy").value("user2"))
            .andExpect(jsonPath("$[1].createdAt").value("2024-01-01T12:00:00"));

    }



    @Test
    public void givenNoTagsExist_whenGetAllTags_thenReturnsEmptyList() throws Exception {
        when(tagService.getAllTags()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }




}

