package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    List<Parent> findByStudentId(Long studentId);
}
