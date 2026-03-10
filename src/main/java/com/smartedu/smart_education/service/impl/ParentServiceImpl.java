package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Parent;
import com.smartedu.smart_education.repository.ParentRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.service.ParentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepo;
    private final StudentRepository studentRepo;

    public ParentServiceImpl(ParentRepository parentRepo, StudentRepository studentRepo) {
        this.parentRepo = parentRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Parent getParentById(Long id) {
        return parentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + id));
    }

    @Override
    public List<Parent> getParentsByStudent(Long studentId) {  // fixed capitalization
        return parentRepo.findByStudentId(studentId);
    }

    @Override
    public Parent addParent(Parent parent) {
        studentRepo.findById(parent.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return parentRepo.save(parent);
    }

    @Override
    public Parent updateParent(Long id, Parent updatedParent) {
        Parent existingParent = getParentById(id);
        existingParent.setPhone(updatedParent.getPhone());
        existingParent.setRelation(updatedParent.getRelation());
        return parentRepo.save(existingParent);
    }

    @Override
    public void deleteParent(Long id) {
        Parent parent = getParentById(id);
        parentRepo.delete(parent);
    }
}