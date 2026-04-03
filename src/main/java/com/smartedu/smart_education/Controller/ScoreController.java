package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.ScoreRequest;
import com.smartedu.smart_education.dto.response.ScoreResponse;
import com.smartedu.smart_education.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ScoreResponse>> getScoresByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(scoreService.getScoresByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<ScoreResponse>> getScoresByStudentAndSubject(@PathVariable Long studentId,
                                                                            @PathVariable Long subjectId) {
        return ResponseEntity.ok(scoreService.getScoresByStudentAndSubject(studentId, subjectId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}/average")
    public ResponseEntity<Double> getAverageMarks(@PathVariable Long studentId,
                                                  @PathVariable Long subjectId) {
        return ResponseEntity.ok(scoreService.getAverageMarks(studentId, subjectId));
    }

    @GetMapping("/weak")
    public ResponseEntity<List<ScoreResponse>> getWeakScores(@RequestParam Long studentId,
                                                             @RequestParam(required = false) Double threshold) {
        return ResponseEntity.ok(scoreService.getWeakScores(studentId, threshold));
    }

    @PostMapping
    public ResponseEntity<ScoreResponse> addScore(@Valid @RequestBody ScoreRequest request) {
        return ResponseEntity.status(201).body(scoreService.addScore(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreResponse> updateScore(@PathVariable Long id,
                                                     @Valid @RequestBody ScoreRequest request) {
        return ResponseEntity.ok(scoreService.updateScore(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return ResponseEntity.noContent().build();
    }
}