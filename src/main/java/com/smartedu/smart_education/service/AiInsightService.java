package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.AiInsight;

import java.util.List;

public interface AiInsightService {
    AiInsight generateInsight(Long studentId);
    AiInsight getLatestInsight(Long studentId);
    List<AiInsight> getAllInsightsByStudent(Long studentId);
    void deleteInsight(Long id);
}
