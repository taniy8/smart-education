package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.Score;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoreResponse {
    private Long id;
    private BigDecimal marks;
    private BigDecimal maxMarks;
    private String examType;
    private LocalDate examDate;
    private String remarks;
    private String studentName;
    private String subjectName;
    private Double percentage;

    public static ScoreResponse fromEntity(Score score) {
        ScoreResponse response = new ScoreResponse();
        response.setId(score.getId());
        response.setMarks(score.getMarks());
        response.setMaxMarks(score.getMaxMarks());
        response.setExamType(score.getExamType().name());
        response.setExamDate(score.getExamDate());
        response.setRemarks(score.getRemarks());
        if (score.getStudent() != null && score.getStudent().getUser() != null) {
            response.setStudentName(score.getStudent().getUser().getName());
        }
        if (score.getSubject() != null) {
            response.setSubjectName(score.getSubject().getName());
        }
        if (score.getMarks() != null && score.getMaxMarks() != null) {
            response.setPercentage(score.getMarks()
                    .divide(score.getMaxMarks(), 2, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue());
        }
        return response;
    }
}