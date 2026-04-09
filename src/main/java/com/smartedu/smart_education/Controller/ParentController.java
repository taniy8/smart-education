package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.ParentRequest;
import com.smartedu.smart_education.dto.response.ParentResponse;
import com.smartedu.smart_education.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @GetMapping("/{id}")
    public ResponseEntity<ParentResponse> getParentById(@PathVariable Long id) {
        return ResponseEntity.ok(parentService.getParentById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ParentResponse>> getParentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(parentService.getParentsByStudent(studentId));
    }

    @PostMapping
    public ResponseEntity<ParentResponse> addParent(@Valid @RequestBody ParentRequest request) {
        return ResponseEntity.status(201).body(parentService.addParent(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentResponse> updateParent(@PathVariable Long id,
                                                       @Valid @RequestBody ParentRequest request) {
        return ResponseEntity.ok(parentService.updateParent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }
}