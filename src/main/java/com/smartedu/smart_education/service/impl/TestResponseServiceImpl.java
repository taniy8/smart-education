package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.TestResponse;
import com.smartedu.smart_education.repository.PersonalizedTestRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.TestResponseRepository;
import com.smartedu.smart_education.service.TestResponseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TestResponseServiceImpl implements TestResponseService {
private final TestResponseRepository testResponseRepo;
private final StudentRepository studentRepo;
private final PersonalizedTestRepository testRepo;

    public TestResponseServiceImpl(TestResponseRepository testResponseRepo, StudentRepository studentRepo, PersonalizedTestRepository testRepo) {
        this.testResponseRepo = testResponseRepo;
        this.studentRepo = studentRepo;
        this.testRepo = testRepo;
    }

    @Override
    public TestResponse submitResponse(TestResponse testResponse) {
        studentRepo.findById(testResponse.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        testRepo.findById(testResponse.getTest().getId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        return testResponseRepo.save(testResponse);
    }

    @Override
    public TestResponse getResponseById(Long id) {
        return testResponseRepo.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
    }

    @Override
    public List<TestResponse> getResponsesByStudent(Long studentId) {
        return testResponseRepo.findByStudentId(studentId);
    }

    @Override
    public List<TestResponse> getResponsesByTest(Long testId) {
        return testResponseRepo.findByTestId(testId);
    }
}

