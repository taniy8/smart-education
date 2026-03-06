package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);
    Student getStudentById(Long id);
    Student getStudentByRollNumber(String rollNumber);
    List<Student> getAllStudents();
    List<Student> getStudentsByClass(String className);
    List<Student> getStudentsByClassAndSection(String className, String section);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}
