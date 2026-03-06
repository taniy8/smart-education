package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Score;

import java.util.List;

public interface ScoreService {
    Score addScore(Score score);
    Score getScoreById(Long id);
    List<Score> getScoresByStudent(Long studentId);
    List<Score> getScoresByStudentAndSubject(Long studentId, Long subjectId);
    Double getAverageMarks(Long studentId, Long subjectId);
    List<Score> getWeakScores(Long studentId, Double threshold);
    Score updateScore(Long id, Score score);
    void deleteScore(Long id);
}
