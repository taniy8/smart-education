package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.QuizScore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuizScoreResponse {
    private Long id;
    private String quizName;
    private BigDecimal score;
    private BigDecimal maxScore;
    private Double percentage;
    private String studentName;
    private String subjectName;

    public static QuizScoreResponse fromEntity(QuizScore quizScore) {
        QuizScoreResponse response = new QuizScoreResponse();
        response.setId(quizScore.getId());
        response.setQuizName(quizScore.getQuizName());
        response.setScore(quizScore.getScore());
        response.setMaxScore(quizScore.getMaxScore());
        if (quizScore.getScore() != null && quizScore.getMaxScore() != null) {
            response.setPercentage(quizScore.getScore()
                    .divide(quizScore.getMaxScore(), 2, java.math.RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue());
        }
        if (quizScore.getStudent() != null && quizScore.getStudent().getUser() != null) {
            response.setStudentName(quizScore.getStudent().getUser().getName());
        }
        if (quizScore.getSubject() != null) {
            response.setSubjectName(quizScore.getSubject().getName());
        }
        return response;
    }
}