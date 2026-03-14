package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.PersonalizedTest;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.repository.PersonalizedTestRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import com.smartedu.smart_education.service.PersonalizedTestService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PersonalizedTestServiceImpl implements PersonalizedTestService {
    private final StudentRepository studentRepo;
    private final PersonalizedTestRepository testRepo;
    private final SubjectRepository subjectRepo;

    public PersonalizedTestServiceImpl(StudentRepository studentRepo, PersonalizedTestRepository testRepo, SubjectRepository subjectRepo) {
        this.studentRepo = studentRepo;
        this.testRepo = testRepo;
        this.subjectRepo = subjectRepo;

    }

    @Override
    public PersonalizedTest generateTest(Long studentId, Long subjectId, PersonalizedTest.Difficulty difficulty) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));

        // Create test and set proper relationships
        PersonalizedTest test = new PersonalizedTest();
        test.setStudent(student);
        test.setSubject(subject);
        test.setDifficulty(difficulty);
        // TODO: Call OpenAI GPT to generate questions in AI Integration phase

        return testRepo.save(test);

    }

    @Override
    public PersonalizedTest getTestById(Long id) {
        return testRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found with id: " + id));
    }

    @Override
    public List<PersonalizedTest> getTestsByStudent(Long studentId) {
        return testRepo.findByStudentId(studentId);
    }

    @Override
    public List<PersonalizedTest> getTestsByStudentAndSubject(Long studentId, Long subjectId) {
        return testRepo.findByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public void deleteTest(Long id) {
        PersonalizedTest test = getTestById(id);
        testRepo.delete(test);
    }
}
