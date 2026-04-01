package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.StudentRequest;
import com.smartedu.smart_education.dto.response.StudentResponse;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final UserRepository userRepo;

    @Override
    public StudentResponse addStudent(StudentRequest request) {
        if (studentRepo.existsByRollNumber(request.getRollNumber())) {
            throw new RuntimeException("Roll number already exists: " + request.getRollNumber());
        }
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

        Student student = new Student();
        student.setRollNumber(request.getRollNumber());
        student.setClassName(request.getClassName());
        student.setSection(request.getSection());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());
        student.setUser(user);

        return StudentResponse.fromEntity(studentRepo.save(student));
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return StudentResponse.fromEntity(studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id)));
    }

    @Override
    public StudentResponse getStudentByRollNumber(String rollNumber) {
        return StudentResponse.fromEntity(studentRepo.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with roll number: " + rollNumber)));
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepo.findAll().stream()
                .map(StudentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getStudentsByClass(String className) {
        return studentRepo.findByClassName(className).stream()
                .map(StudentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getStudentsByClassAndSection(String className, String section) {
        return studentRepo.findByClassNameAndSection(className, section).stream()
                .map(StudentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student existing = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
        existing.setClassName(request.getClassName());
        existing.setSection(request.getSection());
        existing.setPhone(request.getPhone());
        existing.setAddress(request.getAddress());
        existing.setDateOfBirth(request.getDateOfBirth());
        return StudentResponse.fromEntity(studentRepo.save(existing));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
        studentRepo.delete(student);
    }
}