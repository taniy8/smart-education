package com.smartedu.smart_education.repository;

import com.smartedu.smart_education.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Boolean existsByEmployeeCode(String employeeCode);
    Optional<Teacher> findByEmployeeCode(String employeeCode);
   List<Teacher> findByDepartment(String department);
}
