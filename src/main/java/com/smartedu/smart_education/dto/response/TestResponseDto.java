package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.TestResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TestResponseDto {
    private Long id;
    private String studentName;
    private String answers;
    private BigDecimal score;
    private LocalDateTime submittedOn;

    public static TestResponseDto fromEntity(TestResponse testResponse) {
        TestResponseDto dto = new TestResponseDto();
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