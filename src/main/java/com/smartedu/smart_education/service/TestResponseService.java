package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.response.TestResponseDto;
import com.smartedu.smart_education.entity.TestResponse;

import java.util.List;

public interface TestResponseService {
    TestResponseDto submitResponse(TestResponse testResponse);
    TestResponseDto getResponseById(Long id);
    List<TestResponseDto> getResponsesByStudent(Long studentId);
    List<TestResponseDto> getResponsesByTest(Long testId);
}