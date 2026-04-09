package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.request.QuizScoreRequest;
import com.smartedu.smart_education.dto.response.QuizScoreResponse;
import com.smartedu.smart_education.service.QuizScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-scores")
@RequiredArgsConstructor
public class QuizScoreController {

    private final QuizScoreService quizScoreService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizScoreResponse> getQuizScoreById(@PathVariable Long id) {
        return ResponseEntity.ok(quizScoreService.getQuizScoreById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizScoreResponse>> getQuizScoresByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(quizScoreService.getQuizScoresByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<QuizScoreResponse>> getQuizScoresByStudentAndSubject(@PathVariable Long studentId,
                                                                                    @PathVariable Long subjectId) {
        return ResponseEntity.ok(quizScoreService.getQuizScoresByStudentAndSubject(studentId, subjectId));
    }

    @PostMapping
    public ResponseEntity<QuizScoreResponse> addQuizScore(@Valid @RequestBody QuizScoreRequest request) {
        return ResponseEntity.status(201).body(quizScoreService.addQuizScore(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizScore(@PathVariable Long id) {
        quizScoreService.deleteQuizScore(id);
        return ResponseEntity.noContent().build();
    }
}