package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.SubjectRequest;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

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
        return ResponseEntity.ok(subjectService.getSubjectsByClass(className));
    }

    @PostMapping
    public ResponseEntity<Subject> addSubject(@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.status(201).body(subjectService.addSubject(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id,
                                                 @Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
}