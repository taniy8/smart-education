package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.response.AiInsightResponse;
import com.smartedu.smart_education.service.AiInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class AiInsightController {

    private final AiInsightService aiInsightService;

    @PostMapping("/generate/{studentId}")
    public ResponseEntity<AiInsightResponse> generateInsight(@PathVariable Long studentId) {
        return ResponseEntity.status(201).body(aiInsightService.generateInsight(studentId));
    }

    @GetMapping("/student/{studentId}/latest")
    public ResponseEntity<AiInsightResponse> getLatestInsight(@PathVariable Long studentId) {
        return ResponseEntity.ok(aiInsightService.getLatestInsight(studentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AiInsightResponse>> getAllInsightsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(aiInsightService.getAllInsightsByStudent(studentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsight(@PathVariable Long id) {
        aiInsightService.deleteInsight(id);
        return ResponseEntity.noContent().build();
    }
}