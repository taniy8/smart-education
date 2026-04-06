package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.request.ScoreRequest;
import com.smartedu.smart_education.dto.response.ScoreResponse;
import com.smartedu.smart_education.entity.Score;
import com.smartedu.smart_education.entity.Student;
import com.smartedu.smart_education.entity.Subject;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
import com.smartedu.smart_education.repository.ScoreRepository;
import com.smartedu.smart_education.repository.StudentRepository;
import com.smartedu.smart_education.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepo;

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private SubjectRepository subjectRepo;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    private User mockUser;
    private Student mockStudent;
    private Subject mockSubject;
    private Score mockScore;
    private ScoreRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Taniya");
        mockUser.setEmail("taniya@gmail.com");

        mockStudent = new Student();
        mockStudent.setId(1L);
        mockStudent.setRollNumber("2024001");
        mockStudent.setUser(mockUser);

        mockSubject = new Subject();
        mockSubject.setId(1L);
        mockSubject.setName("Mathematics");
        mockSubject.setCode("MATH101");

        mockScore = new Score();
        mockScore.setId(1L);
        mockScore.setMarks(BigDecimal.valueOf(75));
        mockScore.setMaxMarks(BigDecimal.valueOf(100));
        mockScore.setExamType(Score.ExamType.UNIT_TEST);
        mockScore.setExamDate(LocalDate.now());
        mockScore.setStudent(mockStudent);
        mockScore.setSubject(mockSubject);

        mockRequest = new ScoreRequest();
        mockRequest.setStudentId(1L);
        mockRequest.setSubjectId(1L);
        mockRequest.setMarks(BigDecimal.valueOf(75));
        mockRequest.setMaxMarks(BigDecimal.valueOf(100));
        mockRequest.setExamType(Score.ExamType.UNIT_TEST);
        mockRequest.setExamDate(LocalDate.now());
    }

    @Test
    void shouldAddScoreSuccessfully() {

        when(studentRepo.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(mockSubject));
        when(scoreRepo.save(any(Score.class))).thenReturn(mockScore);

        ScoreResponse response = scoreService.addScore(mockRequest);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(75), response.getMarks());
        assertEquals("Mathematics", response.getSubjectName());
        assertEquals(75.0, response.getPercentage());
        verify(scoreRepo, times(1)).save(any(Score.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        mockRequest.setStudentId(99L);

        assertThrows(ResourceNotFoundException.class,
                () -> scoreService.addScore(mockRequest));
        verify(scoreRepo, never()).save(any(Score.class));
    }

    @Test
    void shouldThrowExceptionWhenSubjectNotFound() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(mockStudent));
        when(subjectRepo.findById(99L)).thenReturn(Optional.empty());
        mockRequest.setSubjectId(99L);

        assertThrows(ResourceNotFoundException.class,
                () -> scoreService.addScore(mockRequest));
        verify(scoreRepo, never()).save(any(Score.class));
    }

    @Test
    void shouldGetScoresByStudentSuccessfully() {
        when(scoreRepo.findByStudentId(1L)).thenReturn(List.of(mockScore));

        List<ScoreResponse> responses = scoreService.getScoresByStudent(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(BigDecimal.valueOf(75), responses.get(0).getMarks());
    }

    @Test
    void shouldGetWeakScoresWithDefaultThreshold() {
        when(scoreRepo.findWeakScores(1L, 40.0)).thenReturn(List.of());

        List<ScoreResponse> responses = scoreService.getWeakScores(1L, null);


        assertNotNull(responses);
        verify(scoreRepo, times(1)).findWeakScores(1L, 40.0);
    }

    @Test
    void shouldDeleteScoreSuccessfully() {

        when(scoreRepo.findById(1L)).thenReturn(Optional.of(mockScore));


        scoreService.deleteScore(1L);


        verify(scoreRepo, times(1)).delete(mockScore);
    }
}