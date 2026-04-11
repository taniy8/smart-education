package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.response.AiInsightResponse;

import java.util.List;

public interface AiInsightService {
    AiInsightResponse generateInsight(Long studentId);
    AiInsightResponse getLatestInsight(Long studentId);
    List<AiInsightResponse> getAllInsightsByStudent(Long studentId);
    void deleteInsight(Long id);
}