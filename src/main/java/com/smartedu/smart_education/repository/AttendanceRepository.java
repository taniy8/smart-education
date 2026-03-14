package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT (COUNT(a) * 100.0 / (SELECT COUNT(a2) FROM Attendance a2 WHERE a2.student.id = :studentId)) " +
            "FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    Double findAttendancePercentage(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    Long countPresentDays(@Param("studentId") Long studentId);
}
