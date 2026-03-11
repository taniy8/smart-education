package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Boolean existsByCode(String code);
    Subject findByClassName(String className);
    Subject findByCode(String code);
}
