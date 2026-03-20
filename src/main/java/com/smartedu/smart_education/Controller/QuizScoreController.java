package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.entity.QuizScore;
import com.smartedu.smart_education.service.QuizScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-scores")
public class QuizScoreController {

    private final QuizScoreService quizScoreService;

    public QuizScoreController(QuizScoreService quizScoreService) {
        this.quizScoreService = quizScoreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizScore> getQuizScoreById(@PathVariable Long id) {
        return ResponseEntity.ok(quizScoreService.getQuizScoreById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizScore>> getQuizScoresByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(quizScoreService.getQuizScoresByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<QuizScore>> getQuizScoresByStudentAndSubject(@PathVariable Long studentId,
                                                                            @PathVariable Long subjectId) {
        return ResponseEntity.ok(quizScoreService.getQuizScoresByStudentAndSubject(studentId, subjectId));
    }

    @PostMapping
    public ResponseEntity<QuizScore> addQuizScore(@RequestBody QuizScore quizScore) {
        return ResponseEntity.status(201).body(quizScoreService.addQuizScore(quizScore));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizScore(@PathVariable Long id) {
        quizScoreService.deleteQuizScore(id);
        return ResponseEntity.noContent().build();
    }
}
