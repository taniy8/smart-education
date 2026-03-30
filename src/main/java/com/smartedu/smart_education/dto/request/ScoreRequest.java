package com.smartedu.smart_education.dto.request;

import com.smartedu.smart_education.entity.Score;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoreRequest {

    @NotNull(message = "Marks is required")
    @DecimalMin(value = "0.0", message = "Marks cannot be negative")
    private BigDecimal marks;

    @NotNull(message = "Max marks is required")
    @DecimalMin(value = "1.0", message = "Max marks must be greater than 0")
    private BigDecimal maxMarks;

    @NotNull(message = "Exam type is required")
    private Score.ExamType examType;

    @NotNull(message = "Exam date is required")
    private LocalDate examDate;

    private String remarks;

    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Subject id is required")
    private Long subjectId;
}