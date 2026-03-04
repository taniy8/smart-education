package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.AiInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiInsightRepository extends JpaRepository<AiInsight,Long> {
}
