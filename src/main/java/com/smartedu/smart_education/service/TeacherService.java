package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher addTeacher(Teacher teacher);
    Teacher updateTeacher(Teacher teacher);
    void deleteTeacher(Long id);
    Teacher getTeacherById(Long id);
    List<Teacher> getAllTeacherByDepartment(String department);
    List<Teacher> getAllTeacher();
    Teacher getTeacherByEmployeeCode(String employeeCode);

}
