package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.TeacherRequest;
import com.smartedu.smart_education.dto.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse addTeacher(TeacherRequest request);
    TeacherResponse getTeacherById(Long id);
    TeacherResponse getTeacherByEmployeeCode(String employeeCode);
    List<TeacherResponse> getAllTeachers();
    List<TeacherResponse> getTeachersByDepartment(String department);
    TeacherResponse updateTeacher(Long id, TeacherRequest request);
    void deleteTeacher(Long id);
}