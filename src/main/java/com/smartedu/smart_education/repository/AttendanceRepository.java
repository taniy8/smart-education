package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);

    Double findAttendancePercentage(Long studentId);

    Long countPresentDays(Long studentId);
}
