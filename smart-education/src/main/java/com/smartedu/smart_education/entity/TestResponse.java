package com.smartedu.smart_education.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private PersonalizedTest test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, columnDefinition = "JSON")
    private String answers;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @CreationTimestamp
    @Column(name = "submitted_on", updatable = false)
    private LocalDateTime submittedOn;
}
