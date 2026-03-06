package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findBySubjectNameContainingIgnoreCase(String name);
    Subject addSubject(Subject subject);
    Subject getSubjectById(Long id);
    void deleteSubjectById(Long id);
    Subject updateSubject(Long id,Subject subject);
    Subject getSubjectByCode(String code);
    Subject getSubjectByClass(String className);
}
