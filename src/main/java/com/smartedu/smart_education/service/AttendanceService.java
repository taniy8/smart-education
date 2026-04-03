package com.smartedu.smart_education.service;

import com.smartedu.smart_education.dto.request.AttendanceRequest;
import com.smartedu.smart_education.dto.response.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse markAttendance(AttendanceRequest request);
    List<AttendanceResponse> getAttendanceByStudent(Long studentId);
    List<AttendanceResponse> getAttendanceBetweenDates(Long studentId, LocalDate start, LocalDate end);
    Double getAttendancePercentage(Long studentId);
    Long getTotalPresentDays(Long studentId);
    AttendanceResponse updateAttendance(Long id, AttendanceRequest request);
}