package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore,Long> {
}
