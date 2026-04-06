package com.smartedu.smart_education.service.impl;

import com.smartedu.smart_education.dto.response.UserResponse;
import com.smartedu.smart_education.entity.User;
import com.smartedu.smart_education.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Taniya");
        mockUser.setEmail("taniya@gmail.com");
        mockUser.setRole(User.Role.ADMIN);
        mockUser.setIsActive(true);
    }

    @Test
    void shouldGetUserByIdSuccessfully() {

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));


        UserResponse response = userService.getUserById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Taniya", response.getName());
        assertEquals("taniya@gmail.com", response.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(99L));
    }

    @Test
    void shouldGetActiveUsersSuccessfully() {

        when(userRepo.findByisActiveTrue()).thenReturn(List.of(mockUser));

        List<UserResponse> responses = userService.getActiveUsers();


        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Taniya", responses.get(0).getName());
    }

    @Test
    void shouldGetUsersByRoleSuccessfully() {

        when(userRepo.findByRole(User.Role.ADMIN)).thenReturn(List.of(mockUser));


        List<UserResponse> responses = userService.getUsersByRole(User.Role.ADMIN);


        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(User.Role.ADMIN, responses.get(0).getRole());
    }

    @Test
    void shouldDeactivateUserSuccessfully() {

        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));


        userService.deactivateUser(1L);


        assertFalse(mockUser.getIsActive());
        verify(userRepo, times(1)).save(mockUser);
    }

    @Test
    void shouldActivateUserSuccessfully() {

        mockUser.setIsActive(false);
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));


        userService.activateUser(1L);


        assertTrue(mockUser.getIsActive());
        verify(userRepo, times(1)).save(mockUser);
    }
}