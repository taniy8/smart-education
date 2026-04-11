package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.response.PersonalizedTestResponse;
import com.smartedu.smart_education.entity.PersonalizedTest;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.exception.AiServiceException;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
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
import java.util.stream.Collectors;

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
    public PersonalizedTestResponse generateTest(Long studentId, Long subjectId,
                                                 PersonalizedTest.Difficulty difficulty) {
        // Get student and subject
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));

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

        return PersonalizedTestResponse.fromEntity(testRepo.save(test));
    }

    @Override
    public PersonalizedTestResponse getTestById(Long id) {
        return PersonalizedTestResponse.fromEntity(testRepo.findById(id)
                .orElseThrow(() -> new AiServiceException("Test", id)));
    }

    @Override
    public List<PersonalizedTestResponse> getTestsByStudent(Long studentId) {
        return testRepo.findByStudentId(studentId).stream()
                .map(PersonalizedTestResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonalizedTestResponse> getTestsByStudentAndSubject(Long studentId, Long subjectId) {
        return testRepo.findByStudentIdAndSubjectId(studentId, subjectId).stream()
                .map(PersonalizedTestResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTest(Long id) {
        PersonalizedTest test = testRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test", id));
        testRepo.delete(test);
    }

    // Build prompt for test generation
    private String buildTestPrompt(Student student, Subject subject,
                                   PersonalizedTest.Difficulty difficulty) {
        return "Generate 5 multiple choice questions for a " +
                difficulty.name() + " level test on " +
                subject.getName() + " for class " +
                student.getClassName() + " student.\n\n" +
                "Return ONLY a valid JSON array, no explanation, no markdown, no extra text:\n" +
                "[{\n" +
                "  \"question\": \"...\",\n" +
                "  \"options\": {\"A\": \"...\", \"B\": \"...\", \"C\": \"...\", \"D\": \"...\"},\n" +
                "  \"answer\": \"A\"\n" +
                "}]";
    }
    // Call OpenAI API
    private String callOpenAI(String prompt) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.3-70b-versatile");
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
            String raw = (String) messageResponse.get("content");
            raw = raw.replaceAll("(?s)```json\\s*", "").replaceAll("```", "").trim();
            return raw;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call OpenAI API: " + e.getMessage());
        }
    }
}