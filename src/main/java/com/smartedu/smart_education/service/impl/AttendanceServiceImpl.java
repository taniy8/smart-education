package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.AttendanceRequest;
import com.smartedu.smart_education.dto.response.AttendanceResponse;
import com.smartedu.smart_education.entity.Attendance;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.AttendanceRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    @Override
    public AttendanceResponse markAttendance(AttendanceRequest request) {
        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", request.getStudentId()));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        attendance.setStatus(request.getStatus());
        attendance.setMarkedBy(request.getMarkedBy());

        if (request.getSubjectId() != null) {
            Subject subject = subjectRepo.findById(request.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject", request.getSubjectId()));
            attendance.setSubject(subject);
        }

        return AttendanceResponse.fromEntity(attendanceRepo.save(attendance));
    }

    @Override
    public List<AttendanceResponse> getAttendanceByStudent(Long studentId) {
        return attendanceRepo.findByStudentId(studentId).stream()
                .map(AttendanceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getAttendanceBetweenDates(Long studentId, LocalDate start, LocalDate end) {
        return attendanceRepo.findByStudentIdAndDateBetween(studentId, start, end).stream()
                .map(AttendanceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAttendancePercentage(Long studentId) {
        Double percentage = attendanceRepo.findAttendancePercentage(studentId);
        return percentage != null ? percentage : 0.0;
    }

    @Override
    public Long getTotalPresentDays(Long studentId) {
        Long count = attendanceRepo.countPresentDays(studentId);
        return count != null ? count : 0L;
    }

    @Override
    public AttendanceResponse updateAttendance(Long id, AttendanceRequest request) {
        Attendance existing = attendanceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", id));
        existing.setStatus(request.getStatus());
        return AttendanceResponse.fromEntity(attendanceRepo.save(existing));
    }
}