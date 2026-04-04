package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.SubjectRequest;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepo;

    @Override
    public Subject addSubject(SubjectRequest request) {
        if (subjectRepo.existsByCode(request.getCode())) {
            throw new RuntimeException("Subject code already exists: " + request.getCode());
        }
        Subject subject = new Subject();
        subject.setName(request.getName());
        subject.setCode(request.getCode());
        subject.setClassName(request.getClassName());
        return subjectRepo.save(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", id));
    }

    @Override
    public Subject getSubjectByCode(String code) {
        return subjectRepo.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with code: " + code));
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    @Override
    public List<Subject> getSubjectsByClass(String className) {
        return subjectRepo.findByClassName(className);
    }

    @Override
    public Subject updateSubject(Long id, SubjectRequest request) {
        Subject existing = getSubjectById(id);
        existing.setName(request.getName());
        existing.setClassName(request.getClassName());
        return subjectRepo.save(existing);
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepo.delete(subject);
    }
}