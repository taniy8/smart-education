package com.smartedu.smart_education.Controller;

import com.smartedu.smart_education.dto.response.PersonalizedTestResponse;
import com.smartedu.smart_education.entity.PersonalizedTest;
import com.smartedu.smart_education.service.PersonalizedTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class PersonalizedTestController {

    private final PersonalizedTestService testService;

    @PostMapping("/generate")
    public ResponseEntity<PersonalizedTestResponse> generateTest(@RequestParam Long studentId,
                                                                 @RequestParam Long subjectId,
                                                                 @RequestParam PersonalizedTest.Difficulty difficulty) {
        return ResponseEntity.status(201).body(testService.generateTest(studentId, subjectId, difficulty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalizedTestResponse> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PersonalizedTestResponse>> getTestsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(testService.getTestsByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<PersonalizedTestResponse>> getTestsByStudentAndSubject(@PathVariable Long studentId,
                                                                                      @PathVariable Long subjectId) {
        return ResponseEntity.ok(testService.getTestsByStudentAndSubject(studentId, subjectId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}