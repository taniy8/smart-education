package com.smartedu.smart_education.dto.request;

import com.smartedu.smart_education.entity.Attendance;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {

    @NotNull(message = "Student id is required")
    private Long studentId;

    private Long subjectId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Status is required")
    private Attendance.Status status;

    private Long markedBy;
}