package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore,Long> {
    List<QuizScore> findByStudentId(Long studentId);

    List<QuizScore> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
