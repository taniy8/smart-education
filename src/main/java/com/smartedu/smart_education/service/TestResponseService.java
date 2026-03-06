package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.TestResponse;

import java.util.List;

public interface TestResponseService {
    TestResponse submitResponse(TestResponse testResponse);
    TestResponse getResponseById(Long id);
    List<TestResponse> getResponsesByStudent(Long studentId);
    List<TestResponse> getResponsesByTest(Long testId);
}
