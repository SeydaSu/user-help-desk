package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.model.StatusEntity;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {


    // This interface will automatically inherit methods for CRUD operations
    // from JpaRepository, such as save(), findById(), findAll(), deleteById(), etc.

    StatusEntity findByName(String name);

}