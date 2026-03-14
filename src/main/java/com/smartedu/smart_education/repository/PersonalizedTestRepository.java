package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.PersonalizedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalizedTestRepository extends JpaRepository<PersonalizedTest,Long> {
    List<PersonalizedTest> findByStudentId(Long studentId);

    List<PersonalizedTest> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
