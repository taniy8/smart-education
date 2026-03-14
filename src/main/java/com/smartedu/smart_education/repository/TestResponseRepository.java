package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.TestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResponseRepository extends JpaRepository<TestResponse,Long> {
    List<TestResponse> findByStudentId(Long studentId);

    List<TestResponse> findByTestId(Long testId);
}
