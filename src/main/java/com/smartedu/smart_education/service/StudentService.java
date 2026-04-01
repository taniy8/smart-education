package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.StudentRequest;
import com.smartedu.smart_education.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse addStudent(StudentRequest request);
    StudentResponse getStudentById(Long id);
    StudentResponse getStudentByRollNumber(String rollNumber);
    List<StudentResponse> getAllStudents();
    List<StudentResponse> getStudentsByClass(String className);
    List<StudentResponse> getStudentsByClassAndSection(String className, String section);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
}