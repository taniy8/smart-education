package com.smartedu.smart_education.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TestResponse {
    private Long id;
    private String studentName;
    private String answers;
    private BigDecimal score;
    private LocalDateTime submittedOn;

    public static TestResponse fromEntity(com.smartedu.smart_education.entity.TestResponse testResponse) {
        TestResponse dto = new TestResponse();
        dto.setId(testResponse.getId());
        dto.setAnswers(testResponse.getAnswers());
        dto.setScore(testResponse.getScore());
        dto.setSubmittedOn(testResponse.getSubmittedOn());
        if (testResponse.getStudent() != null && testResponse.getStudent().getUser() != null) {
            dto.setStudentName(testResponse.getStudent().getUser().getName());
        }
        return dto;
    }
}