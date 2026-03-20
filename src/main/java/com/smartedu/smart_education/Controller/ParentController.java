package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Parent;
import com.smartedu.smart_education.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.getParentById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Parent>> getParentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(parentService.getParentsByStudent(studentId));
    }

    @PostMapping
    public ResponseEntity<Parent> addParent(@RequestBody Parent parent) {
        return ResponseEntity.status(201).body(parentService.addParent(parent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable Long id,
                                               @RequestBody Parent parent) {
        return ResponseEntity.ok(parentService.updateParent(id, parent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}
