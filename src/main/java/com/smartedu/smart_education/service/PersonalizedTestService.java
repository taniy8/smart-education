package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.PersonalizedTest;

import java.util.List;

public interface PersonalizedTestService {
    PersonalizedTest generateTest(Long studentId, Long subjectId, PersonalizedTest.Difficulty difficulty);
    PersonalizedTest getTestById(Long id);
    List<PersonalizedTest> getTestsByStudent(Long studentId);
    List<PersonalizedTest> getTestsByStudentAndSubject(Long studentId, Long subjectId);
    void deleteTest(Long id);
}
