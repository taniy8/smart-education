package com.smartedu.smart_education.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartedu.smart_education.entity.AiInsight;
import com.smartedu.smart_education.entity.Score;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.exception.AiServiceException;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.AiInsightRepository;
import com.smartedu.smart_education.repository.ScoreRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.service.AiInsightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AiInsightServiceImpl implements AiInsightService {

    private final AiInsightRepository aiInsightRepo;
    private final StudentRepository studentRepo;
    private final ScoreRepository scoreRepo;

    @Value("${spring.ai.openai.api-key}")
    private String openApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AiInsight generateInsight(Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        List<Score> scores = scoreRepo.findByStudentId(studentId);
        String prompt = buildInsightPrompt(student, scores);
        String gptResponse = callOpenAI(prompt);

        AiInsight insight = new AiInsight();
        insight.setStudent(student);
        insight.setWeakAreas(extractSection(gptResponse, "WEAK AREAS"));
        insight.setStrongAreas(extractSection(gptResponse, "STRONG AREAS"));
        insight.setSuggestions(extractSection(gptResponse, "SUGGESTIONS"));
        insight.setStudyPlan(extractSection(gptResponse, "STUDY PLAN"));
        insight.setParentSummary(extractSection(gptResponse, "PARENT SUMMARY"));

        return aiInsightRepo.save(insight);
    }

    @Override
    public AiInsight getLatestInsight(Long studentId) {
        return aiInsightRepo.findTopByStudentIdOrderByGeneratedOnDesc(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No insights found for student: " + studentId));
    }

    @Override
    public List<AiInsight> getAllInsightsByStudent(Long studentId) {
        return aiInsightRepo.findByStudentId(studentId);
    }

    @Override
    public void deleteInsight(Long id) {
        AiInsight insight = aiInsightRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insight not found with id: " + id));
        aiInsightRepo.delete(insight);
    }

    private String buildInsightPrompt(Student student, List<Score> scores) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an educational AI assistant. Analyze this student's performance:\n\n");
        prompt.append("Student: ").append(student.getUser().getName()).append("\n");
        prompt.append("Class: ").append(student.getClassName()).append("-").append(student.getSection()).append("\n\n");
        prompt.append("Exam Scores:\n");

        for (Score score : scores) {
            double percentage = score.getMarks()
                    .divide(score.getMaxMarks(), 2, java.math.RoundingMode.HALF_UP)
                    .multiply(java.math.BigDecimal.valueOf(100))
                    .doubleValue();
            prompt.append("- ").append(score.getSubject().getName())
                    .append(" (").append(score.getExamType()).append("): ")
                    .append(score.getMarks()).append("/").append(score.getMaxMarks())
                    .append(" (").append(String.format("%.1f", percentage)).append("%)\n");
        }

        prompt.append("\nProvide a detailed analysis in EXACTLY this format:\n\n");
        prompt.append("WEAK AREAS:\n[List subjects where student scored below 60%]\n\n");
        prompt.append("STRONG AREAS:\n[List subjects where student scored above 75%]\n\n");
        prompt.append("SUGGESTIONS:\n[3-5 specific study tips for improvement]\n\n");
        prompt.append("STUDY PLAN:\n[A weekly study plan for improvement]\n\n");
        prompt.append("PARENT SUMMARY:\n[A brief friendly summary for parents]");

        return prompt.toString();
    }

    private String callOpenAI(String prompt) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openApiKey);

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
            return (String) messageResponse.get("content");
        } catch (Exception e) {
            throw new AiServiceException("Failed to call OpenAI API: " + e.getMessage());
        }
    }

    private String extractSection(String response, String sectionName) {
        try {
            int startIndex = response.indexOf(sectionName + ":");
            if (startIndex == -1) return "Not available";

            startIndex = response.indexOf("\n", startIndex) + 1;
            int endIndex = response.indexOf("\n\n", startIndex);

            if (endIndex == -1) endIndex = response.length();
            return response.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "Not available";
        }
    }
}