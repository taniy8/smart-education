package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.service.StudentService;
import com.smartedu.smart_education.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;
    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/roll/{rollNo}")
    public ResponseEntity<Student> getStudentByRollNo(@PathVariable String rollNo) {
        return ResponseEntity.ok(studentService.getStudentByRollNumber(rollNo));
    }
    @GetMapping("/class/{className}")
    public ResponseEntity<List<Student>> getStudentsByClass(@PathVariable String className) {
        return ResponseEntity.ok(studentService.getStudentsByClass(className));
    }

    @GetMapping("/class/{className}/section/{section}")
    public ResponseEntity<List<Student>> getStudentsByClassAndSection(@PathVariable String className,
                                                                      @PathVariable String section) {
        return ResponseEntity.ok(studentService.getStudentsByClassAndSection(className, section));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
