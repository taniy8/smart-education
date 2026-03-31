package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.Attendance;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceResponse {
    private Long id;
    private String studentName;
    private String subjectName;
    private LocalDate date;
    private String status;

    public static AttendanceResponse fromEntity(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setDate(attendance.getDate());
        response.setStatus(attendance.getStatus().name());
        if (attendance.getStudent() != null && attendance.getStudent().getUser() != null) {
            response.setStudentName(attendance.getStudent().getUser().getName());
        }
        if (attendance.getSubject() != null) {
            response.setSubjectName(attendance.getSubject().getName());
        }
        return response;
    }
}