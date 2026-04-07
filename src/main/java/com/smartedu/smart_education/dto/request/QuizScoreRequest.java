package com.smartedu.smart_education.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuizScoreRequest {

    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Subject id is required")
    private Long subjectId;

    @NotBlank(message = "Quiz name is required")
    private String quizName;

    @NotNull(message = "Score is required")
    @DecimalMin(value = "0.0", message = "Score cannot be negative")
    private BigDecimal score;

    @NotNull(message = "Max score is required")
    @DecimalMin(value = "1.0", message = "Max score must be greater than 0")
    private BigDecimal maxScore;
}