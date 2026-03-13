package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Score;
import com.smartedu.smart_education.repository.ScoreRepository;
import com.smartedu.smart_education.service.ScoreService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepo;

    public ScoreServiceImpl(ScoreRepository scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    @Override
    public Score addScore(Score score) {
        scoreRepo.findById(score.getStudent().getId())
                .orElseThrow(()-> new RuntimeException("Student not found"));
        scoreRepo.findById(score.getSubject().getId())
                .orElseThrow(()-> new RuntimeException("Subject not found"));
        return scoreRepo.save(score);
    }

    @Override
    public Score getScoreById(Long id) {
        return scoreRepo.findById(id).orElseThrow(()-> new RuntimeException("Score not found"));
    }

    @Override
    public List<Score> getScoresByStudent(Long studentId) {
        return scoreRepo.findByStudentId(studentId);
    }

    @Override
    public List<Score> getScoresByStudentAndSubject(Long studentId, Long subjectId) {
       return scoreRepo.findByStudentAndSubjectId(studentId,subjectId);
    }

    @Override
    public Double getAverageMarks(Long studentId, Long subjectId) {
        return scoreRepo.findAverageMarksByStudentAndSubjectId(studentId,subjectId);
    }

    @Override
    public List<Score> getWeakScores(Long studentId, Double threshold) {
        if (threshold == null) threshold = 40.0;
        return scoreRepo.findWeakScores(studentId, threshold);
    }

    @Override
    public Score updateScore(Long id, Score updatedScore) {
        Score existing = getScoreById(id);
        existing.setMarks(updatedScore.getMarks());
        existing.setExamType(updatedScore.getExamType());
        existing.setExamDate(updatedScore.getExamDate());
        existing.setRemarks(updatedScore.getRemarks());
        return scoreRepo.save(existing);

    }

    @Override
    public void deleteScore(Long id) {
        Score score = getScoreById(id);
        scoreRepo.delete(score);
    }
}
