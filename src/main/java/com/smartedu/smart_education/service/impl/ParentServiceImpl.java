package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Parent;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.ParentRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.ParentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepo;
    private final StudentRepository studentRepo;
    private final UserRepository userRepo;

    public ParentServiceImpl(ParentRepository parentRepo, StudentRepository studentRepo, UserRepository userRepo) {
        this.parentRepo = parentRepo;
        this.studentRepo = studentRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Parent getParentById(Long id) {
        return parentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with id: " + id));
    }

    @Override
    public List<Parent> getParentsByStudent(Long studentId) {  // fixed capitalization
        return parentRepo.findByStudentId(studentId);
    }

    @Override
    public Parent addParent(Parent parent) {
        studentRepo.findById(parent.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        User user = userRepo.findById(parent.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        parent.setUser(user);
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