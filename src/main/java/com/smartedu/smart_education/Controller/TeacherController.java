package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.TeacherRequest;
import com.smartedu.smart_education.dto.response.TeacherResponse;
import com.smartedu.smart_education.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<TeacherResponse> getTeacherByEmployeeCode(@PathVariable String employeeCode) {
        return ResponseEntity.ok(teacherService.getTeacherByEmployeeCode(employeeCode));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<TeacherResponse>> getTeachersByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(teacherService.getTeachersByDepartment(department));
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> addTeacher(@Valid @RequestBody TeacherRequest request) {
        return ResponseEntity.status(201).body(teacherService.addTeacher(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(@PathVariable Long id,
                                                         @Valid @RequestBody TeacherRequest request) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}