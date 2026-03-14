package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.AiInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiInsightRepository extends JpaRepository<AiInsight,Long> {
    List<AiInsight> findByStudentId(Long studentId);

   Optional<AiInsight> findTopByStudentIdOrderByGeneratedOnDesc(Long studentId);
}
