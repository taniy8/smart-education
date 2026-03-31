package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.Student;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponse {
    private Long id;
    private String rollNumber;
    private String className;
    private String section;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String studentName;
    private String email;

    public static StudentResponse fromEntity(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRollNumber(student.getRollNumber());
        response.setClassName(student.getClassName());
        response.setSection(student.getSection());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setPhone(student.getPhone());
        response.setAddress(student.getAddress());
        if (student.getUser() != null) {
            response.setStudentName(student.getUser().getName());
            response.setEmail(student.getUser().getEmail());
        }
        return response;
    }
}