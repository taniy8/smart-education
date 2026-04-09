package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.QuizScoreRequest;
import com.smartedu.smart_education.dto.response.QuizScoreResponse;

import java.util.List;

public interface QuizScoreService {
    QuizScoreResponse addQuizScore(QuizScoreRequest request);
    QuizScoreResponse getQuizScoreById(Long id);
    List<QuizScoreResponse> getQuizScoresByStudent(Long studentId);
    List<QuizScoreResponse> getQuizScoresByStudentAndSubject(Long studentId, Long subjectId);
    void deleteQuizScore(Long id);
}