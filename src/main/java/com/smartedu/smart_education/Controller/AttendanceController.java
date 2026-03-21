package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Attendance;
import com.smartedu.smart_education.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/percentage")
    public ResponseEntity<Double> getAttendancePercentage(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendancePercentage(studentId));
    }

    @GetMapping("/student/{studentId}/present-days")
    public ResponseEntity<Long> getTotalPresentDays(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getTotalPresentDays(studentId));
    }

    @GetMapping("/student/{studentId}/range")
    public ResponseEntity<List<Attendance>> getAttendanceBetweenDates(@PathVariable Long studentId,
                                                                      @RequestParam LocalDate start,
                                                                      @RequestParam LocalDate end) {
        return ResponseEntity.ok(attendanceService.getAttendanceBetweenDates(studentId, start, end));
    }

    @PostMapping
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.status(201).body(attendanceService.markAttendance(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id,
                                                       @RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendance));
    }
}
