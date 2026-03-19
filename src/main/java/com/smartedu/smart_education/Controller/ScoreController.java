package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.Score;
import com.smartedu.smart_education.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Score>> getScoresByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(scoreService.getScoresByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<Score>> getScoresByStudentAndSubject(@PathVariable Long studentId,
                                                                    @PathVariable Long subjectId) {
        return ResponseEntity.ok(scoreService.getScoresByStudentAndSubject(studentId, subjectId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}/average")
    public ResponseEntity<Double> getAverageMarks(@PathVariable Long studentId,
                                                  @PathVariable Long subjectId) {
        return ResponseEntity.ok(scoreService.getAverageMarks(studentId, subjectId));
    }

    @GetMapping("/weak")
    public ResponseEntity<List<Score>> getWeakScores(@RequestParam Long studentId,
                                                     @RequestParam(required = false) Double threshold) {
        return ResponseEntity.ok(scoreService.getWeakScores(studentId, threshold));
    }

    @PostMapping
    public ResponseEntity<Score> addScore(@RequestBody Score score) {
        return ResponseEntity.status(201).body(scoreService.addScore(score));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable Long id,
                                             @RequestBody Score score) {
        return ResponseEntity.ok(scoreService.updateScore(id, score));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return ResponseEntity.noContent().build();
    }
}
