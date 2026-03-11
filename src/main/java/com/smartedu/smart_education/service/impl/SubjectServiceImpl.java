package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepo;

    public SubjectServiceImpl(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }


    @Override
    public List<Subject> findBySubjectNameContainingIgnoreCase(String name) {
        return subjectRepo.findAll();

    }

    @Override
    public Subject addSubject(Subject subject) {
      if(subjectRepo.existsByCode(subject.getCode())){
          throw  new RuntimeException("Subject Code already exists: " + subject.getCode());
      }
          return subjectRepo.save(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepo.delete(subject);
    }

    @Override
    public Subject updateSubject(Long id, Subject updatedsubject) {
        Subject existingSubject = getSubjectById(id);
        existingSubject.setName(updatedsubject.getName());
        existingSubject.setClassName(updatedsubject.getClassName());
        existingSubject.setCode(updatedsubject.getCode());
        return subjectRepo.save(existingSubject);
    }

    @Override
    public Subject getSubjectByCode(String code) {
        return subjectRepo.findByCode(code);
    }

    @Override
    public Subject getSubjectByClass(String className) {
        return subjectRepo.findByClassName(className);
    }
}
