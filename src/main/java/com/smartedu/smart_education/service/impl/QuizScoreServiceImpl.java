package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.QuizScore;
import com.smartedu.smart_education.repository.QuizScoreRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.QuizScoreService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class QuizScoreServiceImpl implements QuizScoreService {
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final QuizScoreRepository quizScoreRepo;

    public QuizScoreServiceImpl(StudentRepository studentRepo, SubjectRepository subjectRepo, QuizScoreRepository quizScoreRepo) {
        this.studentRepo = studentRepo;
        this.subjectRepo = subjectRepo;
        this.quizScoreRepo = quizScoreRepo;
    }

    @Override
    public QuizScore addQuizScore(QuizScore quizScore) {
       studentRepo.findById(quizScore.getStudent().getId())
               .orElseThrow(()-> new RuntimeException ("Student not found"));
       subjectRepo.findById(quizScore.getSubject().getId())
               .orElseThrow(()-> new RuntimeException ("Subject not found"));
       return quizScoreRepo.save(quizScore);
    }

    @Override
    public QuizScore getQuizScoreById(Long id) {
        return quizScoreRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz score not found with id: " + id));
    }

    @Override
    public List<QuizScore> getQuizScoresByStudent(Long studentId) {
        return quizScoreRepo.findByStudentId(studentId);
    }

    @Override
    public List<QuizScore> getQuizScoresByStudentAndSubject(Long studentId, Long subjectId) {
        return quizScoreRepo.findByStudentIdAndSubjectId(studentId,subjectId);
    }

    @Override
    public void deleteQuizScore(Long id) {
        QuizScore quizScore = getQuizScoreById(id);
        quizScoreRepo.delete(quizScore);
    }
}
