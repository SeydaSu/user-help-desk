package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.model.PriorityEntity;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Long> {


    // This interface will automatically inherit methods for CRUD operations
    // from JpaRepository, such as save(), findById(), findAll(), deleteById(), etc.

    PriorityEntity findByName(String name);

}