package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.PersonalizedTest;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.repository.PersonalizedTestRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.PersonalizedTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonalizedTestServiceImpl implements PersonalizedTestService {

    private final PersonalizedTestRepository testRepo;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PersonalizedTest generateTest(Long studentId, Long subjectId,
                                         PersonalizedTest.Difficulty difficulty) {
        // Get student and subject
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectId));

        // Build prompt
        String prompt = buildTestPrompt(student, subject, difficulty);

        // Call OpenAI
        String questions = callOpenAI(prompt);

        // Save test
        PersonalizedTest test = new PersonalizedTest();
        test.setStudent(student);
        test.setSubject(subject);
        test.setDifficulty(difficulty);
        test.setQuestions(questions);

        return testRepo.save(test);
    }

    @Override
    public PersonalizedTest getTestById(Long id) {
        return testRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found: " + id));
    }

    @Override
    public List<PersonalizedTest> getTestsByStudent(Long studentId) {
        return testRepo.findByStudentId(studentId);
    }

    @Override
    public List<PersonalizedTest> getTestsByStudentAndSubject(Long studentId, Long subjectId) {
        return testRepo.findByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public void deleteTest(Long id) {
        PersonalizedTest test = getTestById(id);
        testRepo.delete(test);
    }

    // Build prompt for test generation
    private String buildTestPrompt(Student student, Subject subject,
                                   PersonalizedTest.Difficulty difficulty) {
        return "Generate 5 multiple choice questions for a " +
                difficulty.name() + " level test on " +
                subject.getName() + " for class " +
                student.getClassName() + " student.\n\n" +
                "Format each question as:\n" +
                "Q1: [Question]\n" +
                "A) [Option]\n" +
                "B) [Option]\n" +
                "C) [Option]\n" +
                "D) [Option]\n" +
                "Answer: [Correct option]\n\n" +
                "Make questions appropriate for the difficulty level.";
    }

    // Call OpenAI API
    private String callOpenAI(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", 1000);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> messageResponse = (Map<String, Object>) firstChoice.get("message");
            return (String) messageResponse.get("content");
        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage());
        }
    }
}