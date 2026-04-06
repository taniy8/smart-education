package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.StudentRequest;
import com.smartedu.smart_education.dto.response.StudentResponse;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    private User mockUser;
    private Student mockStudent;
    private StudentRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Taniya");
        mockUser.setEmail("taniya@gmail.com");
        mockUser.setRole(User.Role.STUDENT);

        mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setRollNumber("2024001");
        mockStudent.setClassName("10");
        mockStudent.setSection("A");
        mockStudent.setDateOfBirth(LocalDate.of(2008, 5, 1));
        mockStudent.setPhone("9876543210");
        mockStudent.setAddress("Delhi");
        mockStudent.setUser(mockUser);

        mockRequest = new StudentRequest();
        mockRequest.setRollNumber("2024001");
        mockRequest.setClassName("10");
        mockRequest.setSection("A");
        mockRequest.setDateOfBirth(LocalDate.of(2008, 5, 1));
        mockRequest.setPhone("9876543210");
        mockRequest.setAddress("Delhi");
        mockRequest.setUserId(1L);
    }

    @Test
    void shouldAddStudentSuccessfully() {
        when(studentRepo.existsByRollNumber("2024001")).thenReturn(false);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(studentRepo.save(any(Student.class))).thenReturn(mockStudent);

        StudentResponse response = studentService.addStudent(mockRequest);

        assertNotNull(response);
        assertEquals("2024001", response.getRollNumber());
        assertEquals("10", response.getClassName());
        assertEquals("Taniya", response.getStudentName());
        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenRollNumberExists() {
        when(studentRepo.existsByRollNumber("2024001")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> studentService.addStudent(mockRequest));
        verify(studentRepo, never()).save(any(Student.class));
    }

    @Test
    void shouldGetStudentByIdSuccessfully() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(mockStudent));

        StudentResponse response = studentService.getStudentById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("2024001", response.getRollNumber());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {

        when(studentRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> studentService.getStudentById(99L));
    }

    @Test
    void shouldGetAllStudentsSuccessfully() {
        when(studentRepo.findAll()).thenReturn(List.of(mockStudent));

        List<StudentResponse> responses = studentService.getAllStudents();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("2024001", responses.get(0).getRollNumber());
    }

    @Test
    void shouldGetStudentsByClassSuccessfully() {

        when(studentRepo.findByClassName("10")).thenReturn(List.of(mockStudent));

        List<StudentResponse> responses = studentService.getStudentsByClass("10");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("10", responses.get(0).getClassName());
    }

    @Test
    void shouldUpdateStudentSuccessfully() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(studentRepo.save(any(Student.class))).thenReturn(mockStudent);

        StudentResponse response = studentService.updateStudent(1L, mockRequest);

        assertNotNull(response);
        verify(studentRepo, times(1)).save(any(Student.class));
    }

    @Test
    void shouldDeleteStudentSuccessfully() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(mockStudent));

        studentService.deleteStudent(1L);

        verify(studentRepo, times(1)).delete(mockStudent);
    }
}