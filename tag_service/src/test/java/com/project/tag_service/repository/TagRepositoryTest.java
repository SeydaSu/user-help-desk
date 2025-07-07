package com.project.tag_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import com.project.model.TagEntity;
import com.project.repository.TagRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void cleanUp() {
        tagRepository.deleteAll();
    }


    @Test
    public void TagRepository_GetAllTags_ReturnsAllTags() {
        TagEntity tag1 = TagEntity.builder()
                .name("tag1")
                .createdBy("user1")
                .createdAt(LocalDateTime.now())
                .build();

        TagEntity tag2 = TagEntity.builder()  
                .name("tag2")
                .createdBy("user2")
                .createdAt(LocalDateTime.now())
                .build();

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        List<TagEntity> tags = tagRepository.findAll();
        assertEquals(2, tags.size());

        
    }

    @Test
    public void TagRepository_FindByName_ReturnsTag() {
        TagEntity tag = TagEntity.builder()
                .name("tag1")
                .createdBy("user1")
                .createdAt(LocalDateTime.now())
                .build();

        tagRepository.save(tag);

        TagEntity foundTag = tagRepository.findByName("tag1");
        assertEquals("tag1", foundTag.getName());
    }


    @Test
    public void TagRepository_FindById_ReturnsTag() {
        TagEntity tag = TagEntity.builder()
                .name("tag1")
                .createdBy("user1")
                .createdAt(LocalDateTime.now())
                .build();

        tag = tagRepository.save(tag);

        TagEntity foundTag = tagRepository.findById(tag.getId()).orElse(null);
        assertEquals(tag.getName(), foundTag.getName());
    }

}
