package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.PersonalizedTest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonalizedTestResponse {
    private Long id;
    private String studentName;
    private String subjectName;
    private String difficulty;
    private String questions;
    private LocalDateTime createdOn;

    public static PersonalizedTestResponse fromEntity(PersonalizedTest test) {
        PersonalizedTestResponse response = new PersonalizedTestResponse();
        response.setId(test.getId());
        response.setDifficulty(test.getDifficulty().name());
        response.setQuestions(test.getQuestions());
        response.setCreatedOn(test.getCreatedOn());
        if (test.getStudent() != null && test.getStudent().getUser() != null) {
            response.setStudentName(test.getStudent().getUser().getName());
        }
        if (test.getSubject() != null) {
            response.setSubjectName(test.getSubject().getName());
        }
        return response;
    }
}