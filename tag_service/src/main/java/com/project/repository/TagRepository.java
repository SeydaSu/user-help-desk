package com.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {


    // This interface will automatically inherit methods for CRUD operations
    // from JpaRepository, such as save(), findById(), findAll(), deleteById(), etc.

    TagEntity findByName(String name);

}
