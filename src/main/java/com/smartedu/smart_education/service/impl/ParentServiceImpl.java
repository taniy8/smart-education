package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.ParentRequest;
import com.smartedu.smart_education.dto.response.ParentResponse;
import com.smartedu.smart_education.entity.Parent;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.ParentRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepo;
    private final UserRepository userRepo;
    private final StudentRepository studentRepo;

    @Override
    public ParentResponse addParent(ParentRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));
        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", request.getStudentId()));

        Parent parent = new Parent();
        parent.setUser(user);
        parent.setStudent(student);
        parent.setRelation(request.getRelation());
        parent.setPhone(request.getPhone());

        return ParentResponse.fromEntity(parentRepo.save(parent));
    }

    @Override
    public ParentResponse getParentById(Long id) {
        return ParentResponse.fromEntity(parentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent", id)));
    }

    @Override
    public List<ParentResponse> getParentsByStudent(Long studentId) {
        return parentRepo.findByStudentId(studentId).stream()
                .map(ParentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ParentResponse updateParent(Long id, ParentRequest request) {
        Parent existing = parentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent", id));
        existing.setPhone(request.getPhone());
        existing.setRelation(request.getRelation());
        return ParentResponse.fromEntity(parentRepo.save(existing));
    }

    @Override
    public void deleteParent(Long id) {
        Parent parent = parentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent", id));
        parentRepo.delete(parent);
    }
}