package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.entity.Teacher;
import com.smartedu.smart_education.repository.TeacherRepository;
import com.smartedu.smart_education.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepo;

    public TeacherServiceImpl(TeacherRepository teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        if (teacherRepo.existsByEmployeeCode(teacher.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists: " + teacher.getEmployeeCode());
        }
        return teacherRepo.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id,Teacher updatedTeacher) {
        Teacher existing = getTeacherById(id);
        existing.setDepartment(updatedTeacher.getDepartment());
        existing.setPhone(updatedTeacher.getPhone());
        return teacherRepo.save(existing);
    }

    @Override
    public void deleteTeacher(Long id) {

    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    @Override
    public List<Teacher> getAllTeacherByDepartment(String department) {
        return teacherRepo.findByDepartment(department);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepo.findAll();
    }

    @Override
    public Teacher getTeacherByEmployeeCode(String employeeCode) {
        return teacherRepo.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + employeeCode));
    }
}
