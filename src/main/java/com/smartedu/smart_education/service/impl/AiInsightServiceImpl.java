package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.AiInsight;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.repository.AiInsightRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.service.AiInsightService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AiInsightServiceImpl implements AiInsightService {
    private final AiInsightRepository aiInsightRepo;
    private final StudentRepository studentRepo;

    public AiInsightServiceImpl(AiInsightRepository aiInsightRepo, StudentRepository studentRepo) {
        this.aiInsightRepo = aiInsightRepo;
        this.studentRepo = studentRepo;
    }


    @Override
    public AiInsight generateInsight(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        // Create insight and properly set student relationship
        AiInsight insight = new AiInsight();
        insight.setStudent(student);
        // TODO: Call OpenAI GPT here in AI Integration phase
        return aiInsightRepo.save(insight);
    }

    @Override
    public AiInsight getLatestInsight(Long studentId) {
        return aiInsightRepo.findTopByStudentIdOrderByGeneratedOnDesc(studentId)
                .orElseThrow(() -> new RuntimeException("No insights found for student: " + studentId));

    }

    @Override
    public List<AiInsight> getAllInsightsByStudent(Long studentId) {
        return aiInsightRepo.findByStudentId(studentId);
    }

    @Override
    public void deleteInsight(Long id) {
        AiInsight insight = aiInsightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Insight not found with id: " + id));
        aiInsightRepo.delete(insight);
    }
}
