package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.ScoreRequest;
import com.smartedu.smart_education.dto.response.ScoreResponse;
import com.smartedu.smart_education.entity.Score;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.ScoreRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepo;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    @Override
    public ScoreResponse addScore(ScoreRequest request) {
        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", request.getStudentId()));
        Subject subject = subjectRepo.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.getSubjectId()));

        Score score = new Score();
        score.setStudent(student);
        score.setSubject(subject);
        score.setMarks(request.getMarks());
        score.setMaxMarks(request.getMaxMarks());
        score.setExamType(request.getExamType());
        score.setExamDate(request.getExamDate());
        score.setRemarks(request.getRemarks());

        return ScoreResponse.fromEntity(scoreRepo.save(score));
    }

    @Override
    public ScoreResponse getScoreById(Long id) {
        return ScoreResponse.fromEntity(scoreRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score", id)));
    }

    @Override
    public List<ScoreResponse> getScoresByStudent(Long studentId) {
        return scoreRepo.findByStudentId(studentId).stream()
                .map(ScoreResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScoreResponse> getScoresByStudentAndSubject(Long studentId, Long subjectId) {
        return scoreRepo.findByStudentIdAndSubjectId(studentId, subjectId).stream()
                .map(ScoreResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageMarks(Long studentId, Long subjectId) {
        return scoreRepo.findAverageMarksByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public List<ScoreResponse> getWeakScores(Long studentId, Double threshold) {
        if (threshold == null) threshold = 40.0;
        return scoreRepo.findWeakScores(studentId, threshold).stream()
                .map(ScoreResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ScoreResponse updateScore(Long id, ScoreRequest request) {
        Score existing = scoreRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score", id));
        existing.setMarks(request.getMarks());
        existing.setExamType(request.getExamType());
        existing.setExamDate(request.getExamDate());
        existing.setRemarks(request.getRemarks());
        return ScoreResponse.fromEntity(scoreRepo.save(existing));
    }

    @Override
    public void deleteScore(Long id) {
        Score score = scoreRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score", id));
        scoreRepo.delete(score);
    }
}