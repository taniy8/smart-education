package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.AttendanceRequest;
import com.smartedu.smart_education.dto.response.AttendanceResponse;
import com.smartedu.smart_education.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByStudent(@PathVariable Long studentId) {
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
    public ResponseEntity<List<AttendanceResponse>> getAttendanceBetweenDates(@PathVariable Long studentId,
                                                                              @RequestParam LocalDate start,
                                                                              @RequestParam LocalDate end) {
        return ResponseEntity.ok(attendanceService.getAttendanceBetweenDates(studentId, start, end));
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.status(201).body(attendanceService.markAttendance(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendance(@PathVariable Long id,
                                                               @Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, request));
    }
}