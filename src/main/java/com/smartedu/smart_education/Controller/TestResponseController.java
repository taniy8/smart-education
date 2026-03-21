package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.TestResponse;
import com.smartedu.smart_education.service.TestResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/test-responses")
public class TestResponseController {
    private final TestResponseService testResponseService;

    public TestResponseController(TestResponseService testResponseService) {
        this.testResponseService = testResponseService;
    }

    @PostMapping
    public ResponseEntity<TestResponse> submitResponse(@RequestBody TestResponse testResponse) {
        return ResponseEntity.status(201).body(testResponseService.submitResponse(testResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(testResponseService.getResponseById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TestResponse>> getResponsesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(testResponseService.getResponsesByStudent(studentId));
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<TestResponse>> getResponsesByTest(@PathVariable Long testId) {
        return ResponseEntity.ok(testResponseService.getResponsesByTest(testId));
    }
}
