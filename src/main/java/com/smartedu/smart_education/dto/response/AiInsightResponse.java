package com.smartedu.smart_education.dto.response;

import com.smartedu.smart_education.entity.AiInsight;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AiInsightResponse {
    private Long id;
    private String studentName;
    private String weakAreas;
    private String strongAreas;
    private String suggestions;
    private String studyPlan;
    private String parentSummary;
    private LocalDateTime generatedOn;

    public static AiInsightResponse fromEntity(AiInsight insight) {
        AiInsightResponse response = new AiInsightResponse();
        response.setId(insight.getId());
        response.setWeakAreas(insight.getWeakAreas());
        response.setStrongAreas(insight.getStrongAreas());
        response.setSuggestions(insight.getSuggestions());
        response.setStudyPlan(insight.getStudyPlan());
        response.setParentSummary(insight.getParentSummary());
        response.setGeneratedOn(insight.getGeneratedOn());
        if (insight.getStudent() != null && insight.getStudent().getUser() != null) {
            response.setStudentName(insight.getStudent().getUser().getName());
        }
        return response;
    }
}