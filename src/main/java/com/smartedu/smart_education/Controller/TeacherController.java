package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Teacher;
import com.smartedu.smart_education.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeacher());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }
    @GetMapping("/code/{employeecode}")
    public ResponseEntity<Teacher> getTeacherByEmployeeCode(@PathVariable String employeeCode) {
        return ResponseEntity.ok(teacherService.getTeacherByEmployeeCode(employeeCode));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Teacher>> getTeachersByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(teacherService.getAllTeacherByDepartment(department));
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.status(201).body(teacherService.addTeacher(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id,
                                                 @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

}
