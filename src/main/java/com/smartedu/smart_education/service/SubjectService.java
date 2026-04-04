package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.SubjectRequest;
import com.smartedu.smart_education.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectRequest request);
    Subject getSubjectById(Long id);
    Subject getSubjectByCode(String code);
    List<Subject> getAllSubjects();
    List<Subject> getSubjectsByClass(String className);
    Subject updateSubject(Long id, SubjectRequest request);
    void deleteSubject(Long id);
}