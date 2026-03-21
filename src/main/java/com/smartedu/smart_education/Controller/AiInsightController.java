package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.AiInsight;
import com.smartedu.smart_education.service.AiInsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insights")
public class AiInsightController {

    private final AiInsightService aiInsightService;

    public AiInsightController(AiInsightService aiInsightService) {
        this.aiInsightService = aiInsightService;
    }

    @PostMapping("/generate/{studentId}")
    public ResponseEntity<AiInsight> generateInsight(@PathVariable Long studentId) {
        return ResponseEntity.status(201).body(aiInsightService.generateInsight(studentId));
    }

    @GetMapping("/student/{studentId}/latest")
    public ResponseEntity<AiInsight> getLatestInsight(@PathVariable Long studentId) {
        return ResponseEntity.ok(aiInsightService.getLatestInsight(studentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AiInsight>> getAllInsightsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(aiInsightService.getAllInsightsByStudent(studentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsight(@PathVariable Long id) {
        aiInsightService.deleteInsight(id);
        return ResponseEntity.noContent().build();
    }
}
