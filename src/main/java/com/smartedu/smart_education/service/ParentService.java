package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Parent;

import java.util.List;

public interface ParentService {
    Parent getParentById(Long id);
    List<Parent> getParentsByStudent(Long studentId);
    Parent addParent(Parent parent);
    Parent updateParent(Long id, Parent parent);
    void deleteParent(Long id);
}