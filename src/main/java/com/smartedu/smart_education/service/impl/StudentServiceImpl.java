package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepo;
    private final StudentRepository studentRepo;

    public StudentServiceImpl(UserRepository userRepo, StudentRepository studentRepo) {
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Student addStudent(Student student) {
       if(studentRepo.existsByRollNumber(student.getRollNumber()))
       {
         throw  new RuntimeException("Student already exists");
       }
        return studentRepo.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student getStudentByRollNumber(String rollNumber) {
        return studentRepo.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found: " + rollNumber));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> getStudentsByClass(String className) {
        return studentRepo.findByClassName(className);
    }

    @Override
    public List<Student> getStudentsByClassAndSection(String className, String section) {
        return studentRepo.findByClassNameAndSection(className,section);
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setClassName(updatedStudent.getClassName());
        existing.setSection(updatedStudent.getSection());
        existing.setPhone(updatedStudent.getPhone());
        existing.setAddress(updatedStudent.getAddress());
        existing.setDateOfBirth(updatedStudent.getDateOfBirth());
        return studentRepo.save(existing);

    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepo.delete(student);
    }
}