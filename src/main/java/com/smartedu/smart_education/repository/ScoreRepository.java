package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {
    List<Score> findByStudentId(Long studentId);
    @Query("SELECT s FROM Score s WHERE s.student.id = :studentId AND s.marks < :threshold")
    List<Score> findWeakScores(@Param("studentId")Long studentId,
                               @Param("threshold") Double threshold);
    @Query("SELECT AVG(s.marks) FROM Score s WHERE s.student.id = :studentId AND s.subject.id = :subjectId")
    Double findAverageMarksByStudentIdAndSubjectId(@Param("studentId") Long studentId,
                                                   @Param("subjectId") Long subjectId);

    List<Score> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
