package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.response.TestResponseDto;
import com.smartedu.smart_education.entity.PersonalizedTest;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.TestResponse;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.PersonalizedTestRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.TestResponseRepository;
import com.smartedu.smart_education.service.TestResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TestResponseServiceImpl implements TestResponseService {

    private final TestResponseRepository testResponseRepo;
    private final StudentRepository studentRepo;
    private final PersonalizedTestRepository testRepo;

    @Override
    public TestResponseDto submitResponse(TestResponse testResponse) {
        studentRepo.findById(testResponse.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student",
                        testResponse.getStudent().getId()));
        testRepo.findById(testResponse.getTest().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Test",
                        testResponse.getTest().getId()));
        return TestResponseDto.fromEntity(testResponseRepo.save(testResponse));
    }

    @Override
    public TestResponseDto getResponseById(Long id) {
        return TestResponseDto.fromEntity(testResponseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestResponse", id)));
    }

    @Override
    public List<TestResponseDto> getResponsesByStudent(Long studentId) {
        return testResponseRepo.findByStudentId(studentId).stream()
                .map(TestResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestResponseDto> getResponsesByTest(Long testId) {
        return testResponseRepo.findByTestId(testId).stream()
                .map(TestResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}