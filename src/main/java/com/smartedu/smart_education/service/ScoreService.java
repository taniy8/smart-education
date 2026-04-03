package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.ScoreRequest;
import com.smartedu.smart_education.dto.response.ScoreResponse;

import java.util.List;

public interface ScoreService {
    ScoreResponse addScore(ScoreRequest request);
    ScoreResponse getScoreById(Long id);
    List<ScoreResponse> getScoresByStudent(Long studentId);
    List<ScoreResponse> getScoresByStudentAndSubject(Long studentId, Long subjectId);
    Double getAverageMarks(Long studentId, Long subjectId);
    List<ScoreResponse> getWeakScores(Long studentId, Double threshold);
    ScoreResponse updateScore(Long id, ScoreRequest request);
    void deleteScore(Long id);
}