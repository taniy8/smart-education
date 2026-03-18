package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Boolean existsByCode(String code);
    Subject findByCode(String code);
    List<Subject> findByClassName(String className);
}
