package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.response.TestResponseDto;
import com.smartedu.smart_education.service.TestResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-responses")
@RequiredArgsConstructor
public class TestResponseController {

    private final TestResponseService testResponseService;

    @PostMapping
    public ResponseEntity<TestResponseDto> submitResponse(@RequestBody com.smartedu.smart_education.entity.TestResponse testResponse) {
        return ResponseEntity.status(201).body(testResponseService.submitResponse(testResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponseDto> getResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(testResponseService.getResponseById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TestResponseDto>> getResponsesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(testResponseService.getResponsesByStudent(studentId));
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<TestResponseDto>> getResponsesByTest(@PathVariable Long testId) {
        return ResponseEntity.ok(testResponseService.getResponsesByTest(testId));
    }
}