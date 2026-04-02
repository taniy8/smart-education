package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.TeacherRequest;
import com.smartedu.smart_education.dto.response.TeacherResponse;
import com.smartedu.smart_education.entity.Teacher;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.TeacherRepository;
import com.smartedu.smart_education.repository.UserRepository;
import com.smartedu.smart_education.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;

    @Override
    public TeacherResponse addTeacher(TeacherRequest request) {
        if (teacherRepo.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists: " + request.getEmployeeCode());
        }
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

        Teacher teacher = new Teacher();
        teacher.setEmployeeCode(request.getEmployeeCode());
        teacher.setDepartment(request.getDepartment());
        teacher.setPhone(request.getPhone());
        teacher.setUser(user);

        return TeacherResponse.fromEntity(teacherRepo.save(teacher));
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        return TeacherResponse.fromEntity(teacherRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", id)));
    }

    @Override
    public TeacherResponse getTeacherByEmployeeCode(String employeeCode) {
        return TeacherResponse.fromEntity(teacherRepo.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + employeeCode)));
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        return teacherRepo.findAll().stream()
                .map(TeacherResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherResponse> getTeachersByDepartment(String department) {
        return teacherRepo.findByDepartment(department).stream()
                .map(TeacherResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        Teacher existing = teacherRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", id));
        existing.setDepartment(request.getDepartment());
        existing.setPhone(request.getPhone());
        return TeacherResponse.fromEntity(teacherRepo.save(existing));
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", id));
        teacherRepo.delete(teacher);
    }
}