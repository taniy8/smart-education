package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    List<Student> findByClassName(String className);
    List<Student> findByClassNameAndSection(String className, String section);
    Boolean existsByRollNumber(String rollNumber);
}
