package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.QuizScore;

import java.util.List;

public interface QuizScoreService {
    QuizScore addQuizScore(QuizScore quizScore);
    QuizScore getQuizScoreById(Long id);
    List<QuizScore> getQuizScoresByStudent(Long studentId);
    List<QuizScore> getQuizScoresByStudentAndSubject(Long studentId, Long subjectId);
    void deleteQuizScore(Long id);
}
