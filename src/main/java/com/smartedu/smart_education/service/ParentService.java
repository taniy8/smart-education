package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Parent;

import java.util.List;

public interface ParentService {
    Parent getParentById(Long id);
    Parent getParentByStudentName(String studentName);
    List<Parent> getParentByClassName(String className);
    Parent getParentByStudentIdAndClassName(String studentId, String className);
    List<Parent> getAllParentsByStudent(Long studentId);
    List<Parent> getAllParentsByClassName(String className);
    Parent addParent(Parent parent);
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);
}
