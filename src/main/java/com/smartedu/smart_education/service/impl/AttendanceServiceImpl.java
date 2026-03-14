package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Attendance;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.repository.AttendanceRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.AttendanceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;
    private final AttendanceRepository attendanceRepo;

    public AttendanceServiceImpl(StudentRepository studentRepo, SubjectRepository subjectRepo, AttendanceRepository attendanceRepo) {
        this.studentRepo = studentRepo;
        this.subjectRepo = subjectRepo;
        this.attendanceRepo = attendanceRepo;
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepo.findByStudentId(studentId);
    }


    @Override
    public Attendance markAttendance(Attendance attendance) {
        studentRepo.findById(attendance.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (attendance.getDate() == null) {
            attendance.setDate(LocalDate.now());
        }
        return attendanceRepo.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceBetweenDates(Long studentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepo.findByStudentIdAndDateBetween(studentId, startDate, endDate);
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
    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        Attendance existing = attendanceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
        existing.setStatus(updatedAttendance.getStatus());
        return attendanceRepo.save(existing);
    }
}
