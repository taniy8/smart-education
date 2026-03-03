package com.smartedu.smart_education.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_insights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "weak_areas", columnDefinition = "TEXT")
    private String weakAreas;

    @Column(name = "strong_areas", columnDefinition = "TEXT")
    private String strongAreas;

    @Column(columnDefinition = "TEXT")
    private String suggestions;

    @Column(name = "study_plan", columnDefinition = "TEXT")
    private String studyPlan;

    @Column(name = "parent_summary", columnDefinition = "TEXT")
    private String parentSummary;

    @CreationTimestamp
    @Column(name = "generated_on", updatable = false)
    private LocalDateTime generatedOn;
}
