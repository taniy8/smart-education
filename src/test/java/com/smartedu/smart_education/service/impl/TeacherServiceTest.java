package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.TeacherRequest;
import com.smartedu.smart_education.dto.response.TeacherResponse;
import com.smartedu.smart_education.entity.Teacher;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.TeacherRepository;
import com.smartedu.smart_education.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private User mockUser;
    private Teacher mockTeacher;
    private TeacherRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Teacher Name");
        mockUser.setEmail("teacher@gmail.com");
        mockUser.setRole(User.Role.TEACHER);

        mockTeacher = new Teacher();
        mockTeacher.setId(1L);
        mockTeacher.setEmployeeCode("EMP001");
        mockTeacher.setDepartment("Science");
        mockTeacher.setPhone("9876543210");
        mockTeacher.setUser(mockUser);

        mockRequest = new TeacherRequest();
        mockRequest.setEmployeeCode("EMP001");
        mockRequest.setDepartment("Science");
        mockRequest.setPhone("9876543210");
        mockRequest.setUserId(1L);
    }

    @Test
    void shouldAddTeacherSuccessfully() {

        when(teacherRepo.existsByEmployeeCode("EMP001")).thenReturn(false);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(teacherRepo.save(any(Teacher.class))).thenReturn(mockTeacher);

        TeacherResponse response = teacherService.addTeacher(mockRequest);

        assertNotNull(response);
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Science", response.getDepartment());
        assertEquals("Teacher Name", response.getTeacherName());
        verify(teacherRepo, times(1)).save(any(Teacher.class));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeCodeExists() {
        when(teacherRepo.existsByEmployeeCode("EMP001")).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> teacherService.addTeacher(mockRequest));
        verify(teacherRepo, never()).save(any(Teacher.class));
    }

    @Test
    void shouldGetTeacherByIdSuccessfully() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(mockTeacher));

        TeacherResponse response = teacherService.getTeacherById(1L);

        assertNotNull(response);
        assertEquals("EMP001", response.getEmployeeCode());
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFound() {
        when(teacherRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> teacherService.getTeacherById(99L));
    }

    @Test
    void shouldGetAllTeachersSuccessfully() {
        when(teacherRepo.findAll()).thenReturn(List.of(mockTeacher));

        List<TeacherResponse> responses = teacherService.getAllTeachers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("EMP001", responses.get(0).getEmployeeCode());
    }

    @Test
    void shouldDeleteTeacherSuccessfully() {
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(mockTeacher));

        teacherService.deleteTeacher(1L);

        verify(teacherRepo, times(1)).delete(mockTeacher);
    }
}