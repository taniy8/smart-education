package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.Teacher;
import lombok.Data;

@Data
public class TeacherResponse {
    private Long id;
    private String employeeCode;
    private String department;
    private String phone;
    private String teacherName;
    private String email;

    public static TeacherResponse fromEntity(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setEmployeeCode(teacher.getEmployeeCode());
        response.setDepartment(teacher.getDepartment());
        response.setPhone(teacher.getPhone());
        if (teacher.getUser() != null) {
            response.setTeacherName(teacher.getUser().getName());
            response.setEmail(teacher.getUser().getEmail());
        }
        return response;
    }
}
