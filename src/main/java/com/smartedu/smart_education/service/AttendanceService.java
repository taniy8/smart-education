package com.smartedu.smart_education.service;

import com.smartedu.smart_education.entity.Attendance;
import com.smartedu.smart_education.entity.Student;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getAttendanceBySubject(String subject);
    List<Attendance> getAttendanceByStudent(Student student);
    List<Attendance> getAttendanceByStatus(String status);
    List<Attendance> getAttendanceByClass(String classCode);
    Attendance markAttendance(Attendance attendance);
    List<Attendance> getAttendanceBetweenDates(Long studentId, LocalDate startDate, LocalDate endDate);
    Double getAttendancePercentage(Long studentId);
    Long getTotalPresentDays(Long studentId);
    Attendance updateAttendance(Long id, Attendance attendance);

}
