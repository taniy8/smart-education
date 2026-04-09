package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.QuizScoreRequest;
import com.smartedu.smart_education.dto.response.QuizScoreResponse;
import com.smartedu.smart_education.entity.QuizScore;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.QuizScoreRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.QuizScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizScoreServiceImpl implements QuizScoreService {

    private final QuizScoreRepository quizScoreRepo;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    @Override
    public QuizScoreResponse addQuizScore(QuizScoreRequest request) {
        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", request.getStudentId()));
        Subject subject = subjectRepo.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", request.getSubjectId()));

        QuizScore quizScore = new QuizScore();
        quizScore.setStudent(student);
        quizScore.setSubject(subject);
        quizScore.setQuizName(request.getQuizName());
        quizScore.setScore(request.getScore());
        quizScore.setMaxScore(request.getMaxScore());

        return QuizScoreResponse.fromEntity(quizScoreRepo.save(quizScore));
    }

    @Override
    public QuizScoreResponse getQuizScoreById(Long id) {
        return QuizScoreResponse.fromEntity(quizScoreRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuizScore", id)));
    }

    @Override
    public List<QuizScoreResponse> getQuizScoresByStudent(Long studentId) {
        return quizScoreRepo.findByStudentId(studentId).stream()
                .map(QuizScoreResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizScoreResponse> getQuizScoresByStudentAndSubject(Long studentId, Long subjectId) {
        return quizScoreRepo.findByStudentIdAndSubjectId(studentId, subjectId).stream()
                .map(QuizScoreResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteQuizScore(Long id) {
        QuizScore quizScore = quizScoreRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuizScore", id));
        quizScoreRepo.delete(quizScore);
    }
}