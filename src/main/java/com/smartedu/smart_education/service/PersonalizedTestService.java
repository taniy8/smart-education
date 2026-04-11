package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.response.PersonalizedTestResponse;
import com.smartedu.smart_education.entity.PersonalizedTest;

import java.util.List;

public interface PersonalizedTestService {
    PersonalizedTestResponse generateTest(Long studentId, Long subjectId, PersonalizedTest.Difficulty difficulty);
    PersonalizedTestResponse getTestById(Long id);
    List<PersonalizedTestResponse> getTestsByStudent(Long studentId);
    List<PersonalizedTestResponse> getTestsByStudentAndSubject(Long studentId, Long subjectId);
    void deleteTest(Long id);
}