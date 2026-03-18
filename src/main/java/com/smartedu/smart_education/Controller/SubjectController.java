package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Subject> getSubjectByCode(@PathVariable String code) {
        return ResponseEntity.ok(subjectService.getSubjectByCode(code));
    }

    @GetMapping("/class/{className}")
    public ResponseEntity<List<Subject>> getSubjectsByClass(@PathVariable String className) {
        return ResponseEntity.ok(subjectService.getSubjectByClass(className));
    }

    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        return ResponseEntity.status(201).body(subjectService.addSubject(subject));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id,
                                                 @RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.updateSubject(id, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}
